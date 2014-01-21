package au.net.asoftware.metamovie.utility;

import java.io.File;
import java.util.List;

public class BoxeeDirectory {

	private String movieDirectory;
	private File nfoFile;
	private File imageFile;
	private File subTitleFile;
	private List<String> movieFile;
	private Integer movieNfoCount;
	private Integer movieJpgCount;
	private Integer movieExtCount;
	private Integer subTitleFileCount;

	public BoxeeDirectory() {
		movieNfoCount = 0;
		movieJpgCount = 0;
		movieExtCount = 0;
		subTitleFileCount = 0;
	}

	/**
	 * @return the movieDirectory
	 */
	public String getMovieDirectory() {
		return movieDirectory;
	}

	/**
	 * @param movieDirectory
	 *            the movieDirectory to set
	 */
	public void setMovieDirectory(String movieDirectory) {
		this.movieDirectory = movieDirectory;
	}

	/**
	 * @return the nfoFile
	 */
	public File getNfoFile() {
		return nfoFile;
	}

	/**
	 * @param nfoFile
	 *            the nfoFile to set
	 */
	public void setNfoFile(File nfoFile) {
		this.nfoFile = nfoFile;
	}

	/**
	 * @return the imageFile
	 */
	public File getImageFile() {
		return imageFile;
	}

	/**
	 * @param imageFile
	 *            the imageFile to set
	 */
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	/**
	 * @return the movieFile
	 */
	public List<String> getMovieFile() {
		return movieFile;
	}

	/**
	 * @param movieFile
	 *            the movieFile to set
	 */
	public void setMovieFile(List<String> movieFile) {
		this.movieFile = movieFile;
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

	/**
	 * @return the subTitleFile
	 */
	public File getSubTitleFile() {
		return subTitleFile;
	}

	/**
	 * @param subTitleFile
	 *            the subTitleFile to set
	 */
	public void setSubTitleFile(File subTitleFile) {
		this.subTitleFile = subTitleFile;
	}

	/**
	 * @return the subTitleFileCount
	 */
	public Integer getSubTitleFileCount() {
		return subTitleFileCount;
	}

	/**
	 * @param subTitleFileCount
	 *            the subTitleFileCount to set
	 */
	public void setSubTitleFileCount(Integer subTitleFileCount) {
		this.subTitleFileCount = subTitleFileCount;
	}

}
