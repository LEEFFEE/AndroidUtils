package cn.huafei.androidutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static String ANDROID_SECURE = "/mnt/sdcard/.android_secure";

	private static final String LOG_TAG = "FileUtil";

	/**SDCard是否已经就绪*/
	public static boolean isSDCardReady() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**文件路径path1是否包含文件路径path2*/
	public static boolean containsPath(String path1, String path2) {
		String path = path2;
		while (path != null) {
			if (path.equalsIgnoreCase(path1))
				return true;
			if (path.equals(Environment.getRootDirectory().getAbsolutePath()))
				break;
			path = new File(path).getParent();
		}
		return false;
	}

	/**拼接路径  返回path/path2	*/
	public static String makePath(String path1, String path2) {
		if (path1.endsWith(File.separator))
			return path1 + path2;

		return path1 + File.separator + path2;
	}

	/**获取扩展名*/
	public static String getExtNameFromFilename(String filename) {
		int dotPosition = filename.lastIndexOf('.');
		if (dotPosition != -1) {
			return filename.substring(dotPosition + 1, filename.length());
		}
		return "";
	}

	/**获取文件名	，不包括扩展名*/
	public static String getNameFromFilename(String filename) {
		int dotPosition = filename.lastIndexOf('.');
		if (dotPosition != -1) {
			return filename.substring(0, dotPosition);
		}
		return "";
	}

	/**获取文件所在的路径*/
	public static String getPathFromFilepath(String filepath) {
		int pos = filepath.lastIndexOf('/');
		if (pos != -1) {
			return filepath.substring(0, pos);
		}
		return "";
	}

	/**获取文件名,不包活文件夹路径，包括扩展名*/
	public static String getNameFromFilepath(String filepath) {
		int pos = filepath.lastIndexOf('/');
		if (pos != -1) {
			return filepath.substring(pos + 1);
		}
		return "";
	}

	/**复制文件  ，若成功，返回新文件所在path，否则返回null*/
	public static String copyFile(String src, String destDir) {
		File file = new File(src);
		if (!file.exists() || file.isDirectory()) {
			Log.v(LOG_TAG, "copyFile: file not exist or is directory, " + src);
			return null;
		}
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			fi = new FileInputStream(file);
			File destPlace = new File(destDir);
			if (!destPlace.exists()) {
				if (!destPlace.mkdirs())
					return null;
			}

			String destPath = FileUtil.makePath(destDir, file.getName());
			File destFile = new File(destPath);
			int i = 1;
			while (destFile.exists()) {
				String destName = FileUtil.getNameFromFilename(file.getName()) + " " + i++ + "."
						+ FileUtil.getExtNameFromFilename(file.getName());
				destPath = FileUtil.makePath(destDir, destName);
				destFile = new File(destPath);
			}

			if (!destFile.createNewFile())
				return null;

			fo = new FileOutputStream(destFile);
			int count = 102400;
			byte[] buffer = new byte[count];
			int read = 0;
			while ((read = fi.read(buffer, 0, count)) != -1) {
				fo.write(buffer, 0, read);
			}

			// TODO: set access privilege

			return destPath;
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "copyFile: file not found, " + src);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(LOG_TAG, "copyFile: " + e.toString());
		} finally {
			try {
				if (fi != null)
					fi.close();
				if (fo != null)
					fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	// does not include sd card folder
	private static String[] SysFileDirs = new String[] { "miren_browser/imagecaches" };

	// comma separated number
	public static String convertNumber(long number) {
		return String.format("%,d", number);
	}

	// storage, G M K B
	public static String convertStorage(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	public static class SDCardInfo {
		public long total;
		public long free;
	}

	/**删除文件或者文件夹*/
	public static boolean deleteFileOrDir(File path) {
		if (path == null || !path.exists()) {
			return true;
		}
		if (path.isFile()) {
			return path.delete();
		}
		File[] files = path.listFiles();
		if (files != null) {
			for (File file : files) {
				deleteFileOrDir(file);
			}
		}
		return path.delete();
	}

	/**获取sdk信息*/
	public static SDCardInfo getSDCardInfo() {
		String sDcString = android.os.Environment.getExternalStorageState();

		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			File pathFile = android.os.Environment.getExternalStorageDirectory();

			try {
				android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());

				// 获取SDCard上BLOCK总数
				long nTotalBlocks = statfs.getBlockCount();

				// 获取SDCard上每个block的SIZE
				long nBlocSize = statfs.getBlockSize();

				// 获取可供程序使用的Block的数量
				long nAvailaBlock = statfs.getAvailableBlocks();

				// 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
				long nFreeBlock = statfs.getFreeBlocks();

				SDCardInfo info = new SDCardInfo();
				// 计算SDCard 总容量大小MB
				info.total = nTotalBlocks * nBlocSize;

				// 计算 SDCard 剩余大小MB
				info.free = nAvailaBlock * nBlocSize;

				return info;
			} catch (IllegalArgumentException e) {
				Log.e(LOG_TAG, e.toString());
			}
		}
		return null;
	}

	/**获取sdcard卡相对路径*/
	public static String getSdDirectory() {
		return Environment.getExternalStorageDirectory().getPath();
	}
}
