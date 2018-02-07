/**
 * SHEpochServie.java 16.11.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

import lamao.soh.SHConstants;
import lamao.soh.core.model.SHEpochLevelItem;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;

/**
 * @author lamao
 */
public class SHEpochService {
    private static final Logger LOGGER = Logger.getLogger(SHUserService.class.getCanonicalName());

    private static final String DEFAULT_EPOCH_CONFIGURATION_FILE = "epoch.xml";

    private SHConstants constants;

    private String epochConfigurationFile = DEFAULT_EPOCH_CONFIGURATION_FILE;

    public SHEpochService(
                    SHConstants constants) {
        this.constants = constants;
    }

    public String getEpochConfigurationFile() {
        return epochConfigurationFile;
    }

    public void setEpochConfigurationFile(String epochConfigurationFile) {
        this.epochConfigurationFile = epochConfigurationFile;
    }

    /**
     * Find all available epochs and load them.
     * @return list of all available epochs
     */
    public List<SHEpoch> getAll() {
        List<SHEpoch> epochs = new LinkedList<SHEpoch>();

        File file = new File(constants.EPOCHS_DIR);
        // build filter for *.xml files
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                File configurationFile = getConfigurationFile(file.getAbsolutePath());
                return file.isDirectory() && configurationFile.exists();
            }
        });

        if (files != null) {
            // load profiles
            SHEpoch epoch = null;
            for (int i = 0; i < files.length; i++) {
                try {
                    epoch = load(getConfigurationFile(files[i].getAbsolutePath()));
                    if (epoch != null) {
                        epoch.setId(getEpochId(files[i].getAbsolutePath()));
                        epochs.add(epoch);
                    }
                } catch (FileNotFoundException e) {
                    LOGGER.warning("Can't load epoch from " + files[i]);
                    e.printStackTrace();
                }
            }

        }

        sortEpochs(epochs);
        return epochs;
    }

    /**
     * Loads epoch info from file
     * @param file epoch configuration file
     * @return epoch info
     * @throws FileNotFoundException if file was not found
     */
    private SHEpoch load(File file) throws FileNotFoundException {
        XStream xstream = new XStream();
        xstream.processAnnotations(SHLevel.class);
        xstream.processAnnotations(SHEpoch.class);

        return (SHEpoch) xstream.fromXML(new FileInputStream(file));
    }

    /**
     * Sort epochs by their order from lowest to highest
     * @param epochs list of epochs to sort
     */
    private void sortEpochs(List<SHEpoch> epochs) {
        Collections.sort(epochs, new Comparator<SHEpoch>() {
            @Override
            public int compare(SHEpoch o1, SHEpoch o2) {
                int result = o1.getOrder() < o2.getOrder() ? -1 : 1;
                return result;
            }
        });
    }

    /**
     * Get epoch configuration file in give directory
     * @param epochDirectory directory with epoch
     * @return {@link File} of epoch configuration
     */
    private File getConfigurationFile(String epochDirectory) {
        File result = new File(epochDirectory + "/" + epochConfigurationFile);
        return result;
    }

    /**
     * Get ID of epoch in given directory
     * @param directory directory with epoch
     * @return ID of epoch (is directory name)
     */
    private String getEpochId(String directory) {
        return directory.substring(directory.lastIndexOf(File.separator) + 1);
    }

    /**
     * Find first epoch where at least one level is uncompleted
     * @param epochs list of epochs to find through
     * @param completedLevels set of ids of completed levels
     * @return first epoch with at least one uncompleted level and first uncompleted level. Or null
     *         if all levels in all epochs were completed
     */
    public SHEpochLevelItem getFirstUncompletedEpoch(
                    List<SHEpoch> epochs,
                    Map<String, Set<String>> completedLevels) {
        for (SHEpoch epoch : epochs) {
            Set<String> completedEpochLevels = completedLevels.get(epoch.getId());
            SHLevel firstUncompletedLevel = getFirstUncompletedLevel(epoch.getLevels(),
                            completedEpochLevels);

            if (firstUncompletedLevel != null) {
                return new SHEpochLevelItem(epoch, firstUncompletedLevel);
            }
        }

        return null;
    }

    /**
     * Find first uncompleted level from given list
     * @param levels list of levels to check. Must be not null and not empty
     * @param completedLevels IDs of completed levels
     * @return first uncompleted level or null if all levels were completed
     */
    public SHLevel getFirstUncompletedLevel(List<SHLevel> levels, Set<String> completedLevels) {
        if (completedLevels == null) {
            return levels.get(0);
        }
        for (SHLevel level : levels) {
            if (!completedLevels.contains(level.getId())) {
                return level;
            }
        }

        return null;
    }
}
