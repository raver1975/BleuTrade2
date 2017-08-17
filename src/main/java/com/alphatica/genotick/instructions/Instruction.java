package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;
import com.alphatica.genotick.processor.Processor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class Instruction implements Serializable {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 6038774498356414583L;

    abstract public void executeOn(Processor processor);

    abstract public void mutate(Mutator mutator);

    public abstract Instruction copy();

    public String instructionString() throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        String objectName = this.getClass().getSimpleName();
        sb.append(objectName).append(" ");
        List<InstructionField> fields = getInheritedFields(this.getClass());
        for(InstructionField field : fields) {
            sb.append(field.getName()).append("=")
                    .append(field.getValue()).append(" ");
        }
        return sb.toString();
    }

    private List<InstructionField> getInheritedFields(Class aClass) throws IllegalAccessException {
        List<InstructionField> fields = new ArrayList<>();
        Class check = aClass;
        while(check != Object.class) {
            Field [] declared = check.getDeclaredFields();
            for(Field field: declared) {
                field.setAccessible(true);
                if(!Modifier.isStatic(field.getModifiers())) {
                    InstructionField instructionField = new InstructionField(field.getName(),field.get(this).toString());
                    fields.add(instructionField);
                }
            }
            check = check.getSuperclass();
        }
        return fields;
    }
}

