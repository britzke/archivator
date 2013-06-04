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

package de.archivator.entities;

/**
 * Ein Markable ist ein Objekt, dass markiert werden kann. Somit kann ggf. eine
 * Bearbeitung bei einer Traversierung einer Ojekt-Liste von Markablen
 * nachvollzogen werden.
 * 
 * @author burghard.britzke bubi@charmides.in-berlin.de
 */
public interface Markable {
	/**
	 * @return the marked
	 */
	public boolean isMarked();

	/**
	 * @param marked
	 *            the marked to set
	 */
	public void setMarked(boolean marked);
}
