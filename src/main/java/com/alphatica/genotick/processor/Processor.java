package com.alphatica.genotick.processor;


import com.alphatica.genotick.instructions.*;

abstract public class Processor {

    abstract public void execute(DivideRegisterByDouble ins);

    abstract public void execute(MultiplyRegisterByDouble ins);

    abstract public void execute(SwapRegisters ins);

    abstract public void execute(MoveDoubleToRegister ins);

    abstract public void execute(ZeroOutRegister ins);

    abstract public void execute(IncrementRegister ins);

    abstract public void execute(DecrementRegister ins);

    abstract public void execute(AddDoubleToRegister ins);

    abstract public void execute(SubtractDoubleFromRegister ins);

    abstract public void execute(DivideVariableByDouble ins);

    abstract public void execute(MultiplyVariableByDouble ins);

    abstract public void execute(SwapVariables ins);

    abstract public void execute(MoveDoubleToVariable ins);

    abstract public void execute(ZeroOutVariable ins);

    abstract public void execute(IncrementVariable ins);

    abstract public void execute(DecrementVariable ins);

    abstract public void execute(AddDoubleToVariable ins);

    abstract public void execute(SubtractDoubleFromVariable ins);

    abstract public void execute(DivideRegisterByRegister ins);

    abstract public void execute(MultiplyRegisterByRegister ins);

    abstract public void execute(AddRegisterToRegister ins);

    abstract public void execute(SubtractRegisterFromRegister ins);

    abstract public void execute(MoveRegisterToRegister ins);

    abstract public void execute(DivideRegisterByVariable ins);

    abstract public void execute(MultiplyRegisterByVariable ins);

    abstract public void execute(AddRegisterToVariable ins);

    abstract public void execute(SubtractRegisterFromVariable ins);

    abstract public void execute(MoveRegisterToVariable ins);

    abstract public void execute(DivideVariableByVariable ins);

    abstract public void execute(MultiplyVariableByVariable ins);

    abstract public void execute(AddVariableToVariable ins);

    abstract public void execute(SubtractVariableFromVariable ins);

    abstract public void execute(MoveVariableToVariable ins);

    abstract public void execute(DivideVariableByRegister ins);

    abstract public void execute(SubtractVariableFromRegister ins);

    abstract public void execute(MoveVariableToRegister ins);

    abstract public void execute(ReturnRegisterAsResult ins);

    abstract public void execute(ReturnVariableAsResult ins);

    @SuppressWarnings("UnusedParameters")
    abstract public void execute(TerminateInstructionList ins);

    abstract public void execute(MoveDataToRegister ins);

    abstract public void execute(MoveDataToVariable ins);

    abstract public void execute(MoveRelativeDataToRegister ins);

    abstract public void execute(MoveRelativeDataToVariable ins);

    abstract public void execute(JumpTo ins);

    abstract public void execute(JumpIfVariableGreaterThanRegister ins);

    abstract public void execute(JumpIfVariableLessThanRegister ins);

    abstract public void  execute(JumpIfVariableEqualRegister ins);

    abstract public void execute(JumpIfVariableNotEqualRegister ins);

    abstract public void execute(JumpIfRegisterEqualRegister ins);

    abstract public void execute(JumpIfRegisterNotEqualRegister ins);

    abstract public void execute(JumpIfRegisterGreaterThanRegister ins);

    abstract public void execute(JumpIfRegisterLessThanRegister ins);

    abstract public void execute(JumpIfVariableGreaterThanVariable ins);

    abstract public void execute(JumpIfVariableLessThanVariable ins);

    abstract public void execute(JumpIfVariableEqualVariable ins);

    abstract public void execute(JumpIfVariableNotEqualVariable ins);

    abstract public void execute(JumpIfVariableGreaterThanDouble ins);

    abstract public void execute(JumpIfVariableLessThanDouble ins);

    abstract public void execute(JumpIfVariableEqualDouble ins);

    abstract public void execute(JumpIfVariableNotEqualDouble ins);

    abstract public void execute(JumpIfRegisterGreaterThanDouble ins);

    abstract public void execute(JumpIfRegisterLessThanDouble ins);

    abstract public void execute(JumpIfRegisterEqualDouble ins);

    abstract public void execute(JumpIfRegisterNotEqualDouble ins);

    abstract public void execute(JumpIfRegisterEqualZero ins);

    abstract public void execute(JumpIfRegisterNotEqualZero ins);

    abstract public void execute(JumpIfRegisterGreaterThanZero ins);

    abstract public void execute(JumpIfRegisterLessThanZero ins);

    abstract public void execute(JumpIfVariableEqualZero ins);

    abstract public void execute(JumpIfVariableNotEqualZero ins);

    abstract public void execute(JumpIfVariableGreaterThanZero ins);

    abstract public void execute(JumpIfVariableLessThanZero ins);

    abstract public void execute(NaturalLogarithmOfData ins);

    abstract public void execute(NaturalLogarithmOfRegister ins);

    abstract public void execute(NaturalLogarithmOfVariable ins);

    abstract public void execute(SqRootOfRegister ins);

    abstract public void execute(SqRootOfVariable ins);

    abstract public void execute(SumOfColumn ins);

    abstract public void execute(AverageOfColumn ins);

    abstract public void execute(HighestOfColumn ins);

    abstract public void execute(LowestOfColumn ins);

}
