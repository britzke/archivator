/*
 * This file is part of archivator, a software system for managing
 * and retrieving archived items.
 *
 * Copyright (C) 2013  burghard.britzke bubi@charmides.in-berlin.de
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.archivator;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;

import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.startup.Tomcat;

/**
 * The EmbeddedArchivator is an embedded solution for the archivator software
 * system.
 * 
 * @author burghard.britzke bubi@charmides.in-berlin.de
 */
public class EmbeddedArchivator {

	/**
	 * Main entry point to the program. Sets up a database connection to the
	 * database specified with the first program argument.
	 * 
	 * @param args
	 *            The first arg is interpreted as filename to the
	 *            Derby-Database.
	 * @throws ServletException
	 * @throws LifecycleException
	 */
	public static void main(String[] args) throws ServletException,
			LifecycleException {
		if (args.length == 1) {
			Tomcat tomcat = new Tomcat();
			tomcat.setPort(8080);

			tomcat.setBaseDir(".");

			Context ctx = tomcat.addWebapp("/archivator",
					new File("WebContent").getAbsolutePath());

			tomcat.enableNaming();

			ContextResource res = new ContextResource();
			res.setName("jdbc/archviatorDB");
			res.setType("javax.sql.DataSource");
			res.setAuth("Container");

			res.setProperty("username", "archivator");
			res.setProperty("password", "archviator");
			res.setProperty("driverClassName",
					"org.apache.derby.jdbc.EmbeddedDriver");

			res.setProperty("url", "jdbc:derby:" + args[0] + ";create=true");
			res.setProperty("maxActive", "2");
			res.setProperty("maxIdle", "1");
			res.setProperty("maxWait", "10000");

			ctx.getNamingResources().addResource(res);

			// ContextEnvironment environment = new ContextEnvironment();
			// environment.setType("java.lang.String");
			// environment.setName("app/exportDir");
			// environment.setValue("c:/exportdir");
			// ctx.getNamingResources().addEnvironment(environment);

			tomcat.start();
			tomcat.getServer().await();
		} else {
			System.out
					.println("usage: java de.archivator.EmbeddedArchivator <path to database file>");
		}
	}
}
