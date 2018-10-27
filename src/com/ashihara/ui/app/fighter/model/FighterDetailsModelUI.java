/**
 * The file FighterDetailsModelUI.java was created on 2009.8.1 at 17:06:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter.model;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FighterPhoto;
import com.ashihara.datamanagement.pojo.AbstractBlob;
import com.ashihara.enums.CM;
import com.ashihara.enums.SC;
import com.ashihara.ui.app.fighter.view.FighterDetailsPanelViewUI;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.core.component.combo.ComboBoxItem;
import com.ashihara.ui.core.file.AllFileFilter;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.resources.ResourceHelper;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.utils.DataManagementUtils;
import com.ashihara.utils.FileUtils;
import com.rtu.exception.PersistenceException;

public class FighterDetailsModelUI extends AKAbstractModelUI<FighterDetailsPanelViewUI> implements IFighterDetailsModelUI<FighterDetailsPanelViewUI>{
	private Fighter fighter;
	private FighterPhoto fighterPhoto;
	private ImageIcon currentSelectedPhoto, noPhoto;
	private FighterDetailsPanelViewUI viewUI;
	private boolean photoLoaded = false;
	private boolean photoDeleted = false;
	
	public FighterDetailsModelUI(Fighter fighter) {
		super();
		setFighter(fighter);
		viewUI = new FighterDetailsPanelViewUI(AKUIEventSender.newInstance(this));
		getViewUI().getModelUI().reset();
	}
	
	public FighterDetailsPanelViewUI getViewUI() {
		return viewUI;
	}
	
	private Fighter getFighter() {
		return fighter;
	}

	private void setFighter(Fighter fighter) {
		this.fighter = fighter;
	}


	protected void copyDataFromUI() {
		getFighter().setName(getViewUI().getTxtName().getText().trim());
		getFighter().setSurname(getViewUI().getTxtSurname().getText().trim());
		getFighter().setWeight(Double.valueOf(getViewUI().getTxtWeight().getText()));
		getFighter().setCountry((Country)getViewUI().getCmbCountry().getSelectedItem());
		getFighter().setBirthday(getViewUI().getDateBirthday().getDate());
		getFighter().setKyu(getViewUI().getTxtKyu().getText().isEmpty() ? null : Long.valueOf(getViewUI().getTxtKyu().getText()));
		getFighter().setDan(getViewUI().getTxtDan().getText().isEmpty() ? null : Long.valueOf(getViewUI().getTxtDan().getText()));
		
		if (getViewUI().getCmbGender().getSelectedItem() instanceof ComboBoxItem) {
			getFighter().setGender(((ComboBoxItem<String>)getViewUI().getCmbGender().getSelectedItem()).getId());
		}
		else {
			getFighter().setGender(null);
		}
	}

	protected void copyDataOnUI() throws IOException {
		getViewUI().getTxtName().setText(getFighter().getName());
		getViewUI().getTxtSurname().setText(getFighter().getSurname());
		getViewUI().getTxtWeight().setText(getFighter().getWeight() == null ? "" : getFighter().getWeight().toString());
		getViewUI().getCmbCountry().tryToSelectIfInEntrySet(getFighter().getCountry());
		getViewUI().getDateBirthday().setDate(getFighter().getBirthday());
		getViewUI().getTxtFullYears().setText(getFighter().getFullYearsOld() == null ? "" : getFighter().getFullYearsOld().toString());
		getViewUI().getImagePanel().setImageIcon(getCurrentSelectedPhoto());
		getViewUI().getTxtKyu().setText(getFighter().getKyu() == null ? "" : getFighter().getKyu().toString());
		getViewUI().getTxtDan().setText(getFighter().getDan() == null ? "" : getFighter().getDan().toString());
		getViewUI().getCmbGender().tryToSelectIfInEntrySet(getFighter().getGender());
	}

	private ImageIcon getImage(AbstractBlob photo) throws IOException {
		if (photo == null) {
			return getNoPhoto();
		}
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(photo.getBlob());
			ImageIcon ii = new ImageIcon(ImageIO.read(bais));
			return ii;
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
			return getNoPhoto();
		}
	}

	protected void validateData() throws AKValidationException {
		Validator.validateMandatoryComponent(getViewUI().getTxtName(), uic.NAME());
		Validator.validate255LengthComponent(getViewUI().getTxtName(), uic.NAME());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtSurname(), uic.SURNAME());
		Validator.validate255LengthComponent(getViewUI().getTxtSurname(), uic.SURNAME());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtWeight(), uic.WEIGHT());
		Validator.validateDataDoubleTypeComponent(getViewUI().getTxtWeight(), new CM.Fighter());
		
		Validator.validateMandatoryComponent(getViewUI().getCmbCountry(), uic.COUNTRY());
		
		Validator.validateMandatoryComponent(getViewUI().getDateBirthday(), uic.BIRTHDAY());
		
		if (getViewUI().getDateBirthday().getDate().getTime() > System.currentTimeMillis()) {
			throw new AKValidationException(uic.BIRTHDAY() +  " - '" + uic.FORMAT_DATE(getViewUI().getDateBirthday().getDate()) + "' "
					+ uic.IS_LATER_THAN_TODAYS_DATE() + " - '" + uic.FORMAT_DATE(new Date()));
		}
		
		if (!getViewUI().getTxtDan().getText().isEmpty() && !getViewUI().getTxtKyu().getText().isEmpty()) {
			throw new AKValidationException(uic.FIELD_() + " '" + uic.KYU() + "' " + uic.OR() + " '" + uic.DAN() + "' "  + uic.MUST_BE_FILLED());
		}
		else if (!getViewUI().getTxtDan().getText().isEmpty()) {
			Validator.validateLong(getViewUI().getTxtDan(), uic.DAN());
			Validator.validateDoubleRangeComponent(getViewUI().getTxtDan(), uic.DAN(), 1d, 10d, true, true);
		}
		else if (!getViewUI().getTxtKyu().getText().isEmpty()) {
			Validator.validateLong(getViewUI().getTxtKyu(), uic.KYU());
			Validator.validateDoubleRangeComponent(getViewUI().getTxtKyu(), uic.KYU(), 1d, 10d, true, true);
		}
		else {
			throw new AKValidationException(uic.FIELD_() + " '" + uic.KYU() + "' " + uic.OR() + " '" + uic.DAN() + "' "  + uic.MUST_BE_FILLED());
		}
		
		Validator.validateMandatoryComponent(getViewUI().getCmbGender(), uic.GENDER());
		
	}

	public void setViewUI(FighterDetailsPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();
	}

	@Override
	public void reset() {
		try {
			photoLoaded = false;
			photoDeleted = false;
			
			ComboUIHelper.reloadCountryCombo(getViewUI().getCmbCountry(), true);
			ComboUIHelper.fillUpCaptionableWithCMItems(getViewUI().getCmbGender(), new SC.GENDER(), true, uic);
			
			if (getFighter().getId() == null){
				setFighter(new Fighter());
			}
			else{
				setFighter(getFighterService().reload(getFighter()));
			}
			
			setFighterPhoto(getPhotoService().loadByFighter(getFighter()));
			
			setCurrentSelectedPhoto(getImage(getFighterPhoto()));
			
			copyDataOnUI();
			
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void save() {
		try {
			validateData();
			copyDataFromUI();
			setFighter(getFighterService().saveFighter(getFighter()));
			
			if (photoDeleted) {
				getPhotoService().deletePhoto(getFighterPhoto());
			}
			if (photoLoaded) {
				getPhotoService().deletePhoto(getFighterPhoto());
				setFighterPhoto(getPhotoService().createNewPhoto(getFighter(), getCurrentSelectedPhoto()));
				setFighterPhoto(getPhotoService().savePhoto(getFighterPhoto()));
			}
			
			cancel();
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void loadPhoto() {
		File file = FileUtils.getOpenPath(new AllFileFilter());
		if (file != null) {
			final long MAX_FILE_SIZE = 5 * 1024;
			long sizeInKb = file.length() / 1024; 
			if (sizeInKb > MAX_FILE_SIZE) {
				MessageHelper.showErrorMessage(getViewUI(), 
						uic.ACTUAL_PHOTO_FILE_SIZE() + " " + sizeInKb + " Kb " + 
						uic.IS_BIGGER_THAN_ALLOWED() + " " + MAX_FILE_SIZE + " Kb");
			}
			else {
				try {
					Image image = ImageIO.read(file);
					if (image != null) {
						ImageIcon ii = new ImageIcon(image);
						getViewUI().getImagePanel().setImageIcon(ii);
						photoLoaded = true;
						photoDeleted = false;
						setCurrentSelectedPhoto(ii);
					}
					else {
						MessageHelper.showErrorMessage(getViewUI(), uic.UNSUPPORTED_FORMAT_OF_PHOTO());
					}
				} catch (IOException e) {
					MessageHelper.handleException(getViewUI(), e);
				}
			}
		}
	}

	@Override
	public void deletePhoto() {
		photoDeleted = true;
		photoLoaded = false;
		getViewUI().getImagePanel().setImageIcon(getNoPhoto());
		setCurrentSelectedPhoto(getNoPhoto());
	}

	private FighterPhoto getFighterPhoto() {
		return fighterPhoto;
	}

	private void setFighterPhoto(FighterPhoto fighterPhoto) {
		this.fighterPhoto = fighterPhoto;
	}

	private ImageIcon getCurrentSelectedPhoto() {
		return currentSelectedPhoto;
	}

	private void setCurrentSelectedPhoto(ImageIcon currentSelectedPhoto) {
		this.currentSelectedPhoto = currentSelectedPhoto;
	}

	public ImageIcon getNoPhoto() {
		if (noPhoto == null) {
			noPhoto = ResourceHelper.getImageIcon(ResourceHelper.NO_PHOTO);
		}
		return noPhoto;
	}
}
