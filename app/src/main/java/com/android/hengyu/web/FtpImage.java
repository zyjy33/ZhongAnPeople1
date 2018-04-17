package com.android.hengyu.web;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpImage {

	public static int ftpUpload(String imgUrl, String directoryname,
			String filename) throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException {
		FTPClient client = new FTPClient();
		client.connect("183.62.138.31", 1423);
		client.login("ftp", "ftp");

		String remotePathTmp = "PhoneImages" + "//" + directoryname;

		try {
			client.createDirectory(remotePathTmp);
		} catch (Exception e) {

		} finally {
			client.changeDirectory(remotePathTmp);
			File file = new File(imgUrl);
			FileInputStream fis = new FileInputStream(file);
			client.upload(filename + ".jpg", fis, 0, 0, null);
			fis.close();
			client.logout();// exit
		}

		return 1;
	}

}
