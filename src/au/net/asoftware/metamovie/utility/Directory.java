package au.net.asoftware.metamovie.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constuctor
 * 
 * @author mick
 * 
 */
public class Directory extends Common {

	private final static Logger logger = LoggerFactory.getLogger(Directory.class.getName());

	private File basePath;
	private List<BoxeeDirectory> boxeeDirectoryList;
	private SortedSet<String> movieExtensions;
	private List<String> movieSubDirectories;
	private List<File> unsupportedMovieFiles;
	private Integer movieCount;
	private Integer movieNfoCount;
	private Integer movieJpgCount;
	private Integer movieExtCount;
	private Integer movieSrtCount;
	private SortedSet<String> supportedVideoTypes;

	public Directory(File basePath) {
		this.basePath = basePath;
		this.boxeeDirectoryList = new ArrayList<BoxeeDirectory>();
		this.movieExtensions = new TreeSet<String>();
		this.movieSubDirectories = new ArrayList<String>();
		this.movieCount = 0;
		this.movieNfoCount = 0;
		this.movieJpgCount = 0;
		this.movieExtCount = 0;
		this.movieSrtCount = 0;

		this.supportedVideoTypes = new TreeSet<String>();
		this.supportedVideoTypes.add("avi");
		this.supportedVideoTypes.add("divx");
		this.supportedVideoTypes.add("m2ts");
		this.supportedVideoTypes.add("m4v");
		this.supportedVideoTypes.add("mkv");
		this.supportedVideoTypes.add("mp4");

		this.unsupportedMovieFiles = new ArrayList<File>();

		this.generateListing();
	}

	/**
	 * @return boxeeDirectoryList
	 */
	private List<BoxeeDirectory> generateListing() {

		File[] topLevelDirectories = basePath.listFiles();

		this.movieCount = topLevelDirectories.length;

		for (File topLevelDirectory : topLevelDirectories) {

			if (!topLevelDirectory.isDirectory()) {
				continue;
			}

			BoxeeDirectory boxeeDirectory = new BoxeeDirectory();
			boxeeDirectory.setMovieDirectory(topLevelDirectory.getName());

			File[] filesInMovieDirectory = topLevelDirectory.listFiles();
			List<String> fileExtInMovieDirectory = new ArrayList<String>();
			Integer i = 0;
			Integer j = 0;
			Integer k = 0;
			for (File fileInMovieDirectory : filesInMovieDirectory) {

				if (fileInMovieDirectory.isDirectory()) {
					movieSubDirectories.add(topLevelDirectory.getName() + File.separator + fileInMovieDirectory.getName());
				}

				if (fileInMovieDirectory.getName().endsWith("nfo")) {
					boxeeDirectory.setNfoFile(fileInMovieDirectory);
					this.movieNfoCount++;
					i++;
				} else if (fileInMovieDirectory.getName().endsWith("jpg")) {

					if (!fileInMovieDirectory.getName().equals("folder.jpg")) {
						if (fileInMovieDirectory.getName().toLowerCase().equals("._folder.jpg")) {
							fileInMovieDirectory.delete();
						} else {
							logger.info("Bad Jpeg name: {}", fileInMovieDirectory.getAbsolutePath());
						}
					} else {
						boxeeDirectory.setImageFile(fileInMovieDirectory);
						this.movieJpgCount++;
						j++;
					}
				} else if (fileInMovieDirectory.getName().endsWith("srt")) {
					boxeeDirectory.setSubTitleFile(fileInMovieDirectory);
					this.movieSrtCount++;
					k++;
				} else if (isSupportedMovieType(fileInMovieDirectory)) {

					String ft = fileInMovieDirectory.getName().replaceFirst(".*\\.", "");

					this.movieExtensions.add(ft.toLowerCase().trim());

					fileExtInMovieDirectory.add(fileInMovieDirectory.getName());
					this.movieExtCount++;

				} else {
					if (fileInMovieDirectory.getName().toLowerCase().endsWith("ds_store")) {
						fileInMovieDirectory.delete();
					} else {
						this.unsupportedMovieFiles.add(fileInMovieDirectory);
					}
				}
				boxeeDirectory.setMovieFile(fileExtInMovieDirectory);
				boxeeDirectory.setMovieNfoCount(i);
				boxeeDirectory.setMovieJpgCount(j);
				boxeeDirectory.setSubTitleFileCount(k);

				boxeeDirectory.setMovieExtCount(fileExtInMovieDirectory.size());
			}

			if (boxeeDirectory != null) {
				boxeeDirectoryList.add(boxeeDirectory);
			}

		}

		return boxeeDirectoryList;
	}

	/**
	 * 
	 * @param dataBasePath
	 */
	public void fullReport(File dataBasePath) {

		Directory parentDirectory = new Directory(dataBasePath);

		logger.info("----------------- Full Boxee Directory Report -----------------");

		for (BoxeeDirectory bd : parentDirectory.getBoxeeDirectoryList()) {

			logger.info(bd.getMovieDirectory());
			logger.info(bd.getNfoFile().getName());
			logger.info(bd.getImageFile().getName());
			for (String gmf : bd.getMovieFile()) {
				logger.info(gmf);
			}
			logger.info("Movie Nfo Count:       [" + bd.getMovieNfoCount() + "]");
			logger.info("Movie JPG Count:       [" + bd.getMovieJpgCount() + "]");
			logger.info("Movie Extension Count: [" + bd.getMovieExtCount() + "]");
			logger.info("---------------------------------------------------------------");
		}
		logger.info("BasePath:        " + parentDirectory.getBasePath());
		logger.info("Movie Ext Types: " + parentDirectory.getMovieExtensions());
		logger.info("Movie Count:     " + parentDirectory.getMovieCount());
		logger.info("Movie Nfo Count: " + parentDirectory.getMovieNfoCount());
		logger.info("Movie Jpg Count: " + parentDirectory.getMovieJpgCount());
		logger.info("Movie Ext Count: " + parentDirectory.getMovieExtCount());
	}

	/**
	 * 
	 * @param dataBasePath
	 */
	public void summaryReport(File dataBasePath) {

		// Directory parentDirectory = new Directory(dataBasePath);
		logger.info("----------------------------------------------------------------");
		logger.info("---------------- Summary Boxee Directory Report ----------------");

		logger.info("Missing NFO ----------------------------------------------------");
		for (BoxeeDirectory bd : this.getBoxeeDirectoryList()) {

			if (bd.getMovieNfoCount() == 0) {
				logger.info("NO NFO - " + bd.getMovieDirectory());
			}
		}

		logger.info("Unsupported Video ----------------------------------------------");
		for (File usuppotredVideoFile : this.unsupportedMovieFiles) {
			logger.info("Unsupported - " + usuppotredVideoFile.getParent() + File.separator + usuppotredVideoFile.getName());
		}

		logger.info("Missing JPG ----------------------------------------------------");
		for (BoxeeDirectory bd : this.getBoxeeDirectoryList()) {

			if (bd.getMovieJpgCount() == 0) {
				logger.info("NO JPG - " + bd.getMovieDirectory());
			}
		}

		logger.info("Multiple Extensions --------------------------------------------");
		for (BoxeeDirectory bd : this.getBoxeeDirectoryList()) {

			if (bd.getMovieExtCount() > 1) {
				logger.info("2 Movie Extension Files - " + bd.getMovieDirectory());
			}

		}

		logger.info("Sub Directories ------------------------------------------------");
		for (String sd : this.getMovieSubDirectories()) {
			logger.info("Sub Directory - {}", sd);
		}

		logger.info("Sub Titles -----------------------------------------------------");
		for (BoxeeDirectory bd : this.getBoxeeDirectoryList()) {

			if (bd.getSubTitleFileCount() > 0) {
				logger.info("SRT Sub Title - " + bd.getMovieDirectory());
			}
		}

		logger.info("---------------------------------------------------------------");
		logger.info("BasePath               : " + this.getBasePath());
		logger.info("Movie Ext Types        : " + this.getMovieExtensions());
		logger.info("Movie Count            : " + this.getMovieCount());
		logger.info("Movie Nfo Count        : " + this.getMovieNfoCount());
		logger.info("Movie Jpg Count        : " + this.getMovieJpgCount());
		logger.info("Movie Ext Count        : " + this.getMovieExtCount());
		logger.info("Movie Srt Count        : " + this.getMovieSrtCount());
		logger.info("Sub Directory Count    : " + this.movieSubDirectories.size());
		logger.info("Unsupported Video Count: " + this.unsupportedMovieFiles.size());
		logger.info("----------------------------------------------------------------");

	}

	/**
	 * Find out if the movie is a supported type
	 * 
	 * @param movieFile
	 * @return
	 */
	public boolean isSupportedMovieType(File movieFile) {
		boolean result = false;

		if (movieFile != null) {
			for (String supportedVideoType : supportedVideoTypes) {
				if (movieFile.getName().trim().toLowerCase().endsWith(supportedVideoType.trim().toLowerCase())) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * @return the basePath
	 */
	public File getBasePath() {
		return basePath;
	}

	/**
	 * @param basePath
	 *            the basePath to set
	 */
	public void setBasePath(File basePath) {
		this.basePath = basePath;
	}

	/**
	 * @return the boxeeDirectoryList
	 */
	public List<BoxeeDirectory> getBoxeeDirectoryList() {
		return boxeeDirectoryList;
	}

	/**
	 * @param boxeeDirectoryList
	 *            the boxeeDirectoryList to set
	 */
	public void setBoxeeDirectoryList(List<BoxeeDirectory> boxeeDirectoryList) {
		this.boxeeDirectoryList = boxeeDirectoryList;
	}

	/**
	 * 
	 * @return
	 */
	public SortedSet<String> getMovieExtensions() {
		return movieExtensions;
	}

	/**
	 * 
	 * @param movieExtensions
	 */
	public void setMovieExtensions(SortedSet<String> movieExtensions) {
		this.movieExtensions = movieExtensions;
	}

	/**
	 * @return the movieCount
	 */
	public Integer getMovieCount() {
		return movieCount;
	}

	/**
	 * @param movieCount
	 *            the movieCount to set
	 */
	public void setMovieCount(Integer movieCount) {
		this.movieCount = movieCount;
	}

	/**
	 * @return the movieNfoCount
	 */
	public Integer getMovieNfoCount() {
		return movieNfoCount;
	}

	/**
	 * @param movieNfoCount
	 *            the movieNfoCount to set
	 */
	public void setMovieNfoCount(Integer movieNfoCount) {
		this.movieNfoCount = movieNfoCount;
	}

	/**
	 * @return the movieJpgCount
	 */
	public Integer getMovieJpgCount() {
		return movieJpgCount;
	}

	/**
	 * @param movieJpgCount
	 *            the movieJpgCount to set
	 */
	public void setMovieJpgCount(Integer movieJpgCount) {
		this.movieJpgCount = movieJpgCount;
	}

	/**
	 * @return the movieExtCount
	 */
	public Integer getMovieExtCount() {
		return movieExtCount;
	}

	/**
	 * @param movieExtCount
	 *            the movieExtCount to set
	 */
	public void setMovieExtCount(Integer movieExtCount) {
		this.movieExtCount = movieExtCount;
	}

	public List<String> getMovieSubDirectories() {
		return movieSubDirectories;
	}

	public void setMovieSubDirectories(List<String> movieSubDirectories) {
		this.movieSubDirectories = movieSubDirectories;
	}

	public Integer getMovieSubDirectoryCount() {
		return movieSubDirectories.size();
	}

	/**
	 * @return the supportedVideoTypes
	 */
	public SortedSet<String> getSupportedVideoTypes() {
		return supportedVideoTypes;
	}

	/**
	 * @return the movieSrtCount
	 */
	public Integer getMovieSrtCount() {
		return movieSrtCount;
	}

	/**
	 * @return the unsupportedMovieFiles
	 */
	public List<File> getUnsupportedMovieFiles() {
		return unsupportedMovieFiles;
	}

}
