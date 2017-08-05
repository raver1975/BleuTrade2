package com.alphatica.genotick.population;

import com.alphatica.genotick.genotick.RandomGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static java.lang.String.format;

public class PopulationDAOFileSystem implements PopulationDAO {
    private static final String FILE_EXTENSION = ".prg";
    private final String robotsPath;
    private final Random random;

    public PopulationDAOFileSystem(String dao) {
        checkPath(dao);
        robotsPath = dao;
        random = RandomGenerator.assignRandom();
    }

    @Override
    public RobotName[] listRobotNames() {
        String [] files = listFiles(robotsPath);
        RobotName[] names = new RobotName[files.length];
        for(int i = 0; i < files.length; i++) {
            String longString = files[i].substring(0,files[i].indexOf('.'));
            names[i] = new RobotName(Long.valueOf(longString));
        }
        return names;
    }

    @Override
    public Robot getRobotByName(RobotName name) {
        File file = createFileForName(name);
        return getRobotFromFile(file);
    }

    @Override
    public Iterable<Robot> getRobotList() {
        return new Iterable<Robot>() {
            class ListAvailableRobots implements Iterator<Robot> {
                final private List<RobotName> names;
                int index = 0;
                ListAvailableRobots() {
                    names = getAllRobotsNames();
                }
                @Override
                public boolean hasNext() {
                    return names.size() > index;
                }

                @Override
                public Robot next() {
                    if(!hasNext()) {
                        throw new NoSuchElementException(format("Unable to get element %d", index+1));
                    }
                    return getRobotByName(names.get(index++));
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("remove() not supported");
                }
            }
            @Override
            public Iterator<Robot> iterator() {
                return new ListAvailableRobots();
            }
        };
    }

    @Override
    public int getAvailableRobotsCount() {
        return getAllRobotsNames().size();
    }

    @Override
    public void saveRobot(Robot robot) {
        if(robot.getName() == null) {
            robot.setName(getAvailableName());
        }
        File file = createFileForName(robot.getName());
        saveRobotToFile(robot,file);
    }

    @Override
    public void removeRobot(RobotName robotName) {
        File file = createFileForName(robotName);
        boolean result = file.delete();
        if(!result)
            throw new DAOException("Unable to remove file " + file.getAbsolutePath());
    }

    public static Robot getRobotFromFile(File file) {
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream( new FileInputStream(file)))) {
            return (Robot) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new DAOException(e);
        }
    }

    private void checkPath(String dao) {
        File file = new File(dao);
        if(!file.exists()){file.mkdir();}
//            throw new DAOException(format("Path '%s' does not exist.",dao));
        if(!file.isDirectory())
            throw new DAOException(format("Path '%s' is not a directory.",dao));
        if(!file.canRead())
            throw new DAOException(format("Path '%s' is not readable.",dao));
    }

    private List<RobotName> getAllRobotsNames() {
        List<RobotName> list = new ArrayList<>();
        String [] fileList = listFiles(robotsPath);
        if(fileList == null)
            return list;
        for(String name: fileList) {
            String shortName = name.split("\\.")[0];
            Long l = Long.parseLong(shortName);
            list.add(new RobotName(l));
        }
        return list;
    }

    private String [] listFiles(String dir) {
        File path = new File(dir);
        return path.list((dir1, name) -> name.endsWith(FILE_EXTENSION));
    }

    private RobotName getAvailableName() {
        File file;
        long l;
        do {
            l = Math.abs(random.nextLong()-1);
            file = new File(robotsPath + String.valueOf(l) + FILE_EXTENSION);
        } while (file.exists());
        return new RobotName(l);
    }

    private File createFileForName(RobotName name) {
        return new File(robotsPath + File.separator + name.toString() + FILE_EXTENSION);
    }

    private void saveRobotToFile(Robot robot, File file)  {
        deleteFileIfExists(file);
        try(ObjectOutputStream ous = new ObjectOutputStream(new BufferedOutputStream( new FileOutputStream(file)))) {
            ous.writeObject(robot);
        } catch (IOException ex) {
            throw new DAOException(ex);
        }
    }

    private void deleteFileIfExists(File file) {
        if(!file.exists())
            return;
        if(!file.delete()) {
            throw new DAOException("Unable to delete file: " + file);
        }
    }


}
