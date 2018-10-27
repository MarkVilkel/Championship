/**
 * The file PhotoServiceImpl.java was created on 2010.3.4 at 14:45:35
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.interfaces.FighterPhotoService;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FighterPhoto;
import com.ashihara.utils.DataManagementUtils;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class FighterPhotoServiceImpl extends AbstractAKServiceImpl implements FighterPhotoService {

	@Override
	public FighterPhoto createNewPhoto(Fighter fighter, ImageIcon imageIcon) throws AKBusinessException, IOException {
		if (fighter == null || fighter.getId() == null || imageIcon == null) {
			return null;
		}
		
		FighterPhoto photo = new FighterPhoto();
		photo.setFighter(fighter);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write((BufferedImage)imageIcon.getImage(), "jpg", baos);
		byte[] b2 = baos.toByteArray();
		
		photo.setBlob(b2);
		
		return photo;
	}

	@Override
	public void deletePhoto(FighterPhoto fighterPhoto) throws PersistenceException {
		getHelper().delete(fighterPhoto);
	}

	@Override
	public FighterPhoto loadByFighter(Fighter fighter) throws PersistenceException {
		if (fighter == null || fighter.getId() == null) {
			return null;
		}
		HqlQuery<FighterPhoto> hql = getHelper().createHqlQuery(FighterPhoto.class, getCmFighterPhoto());
		
		hql.addExpression(ExpressionHelper.eq(getCmFighterPhoto().getFighter(), fighter));
		
		return getHelper().uniqueResult(hql);
	}

	@Override
	public FighterPhoto reload(FighterPhoto fighterPhoto) throws PersistenceException {
		return getHelper().reload(fighterPhoto);
	}

	@Override
	public FighterPhoto savePhoto(FighterPhoto fighterPhoto) throws PersistenceException {
		return getHelper().save(fighterPhoto);
	}

}
