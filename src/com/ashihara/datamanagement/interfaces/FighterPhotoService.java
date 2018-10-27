/**
 * The file PhotoService.java was created on 2010.3.4 at 14:45:16
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.io.IOException;

import javax.swing.ImageIcon;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FighterPhoto;
import com.rtu.exception.PersistenceException;

public interface FighterPhotoService extends AKService {

	public FighterPhoto reload(FighterPhoto fighterPhoto) throws PersistenceException;
	public FighterPhoto loadByFighter(Fighter fighter) throws PersistenceException;
	public void deletePhoto(FighterPhoto fighterPhoto) throws PersistenceException;
	public FighterPhoto createNewPhoto(Fighter fighter, ImageIcon imageIcon) throws AKBusinessException, IOException;
	public FighterPhoto savePhoto(FighterPhoto fighterPhoto) throws PersistenceException;

}
