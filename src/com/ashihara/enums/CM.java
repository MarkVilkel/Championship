package com.ashihara.enums;

import java.util.Date;
import java.util.List;

import com.rtu.persistence.core.Attribute;

// This is automatically generated class file by launching com.ashihara.datamanagement.core.persistence.KasEntityRegister.createConceptualModelFile()
// DO NOT CHANGE IT BY HANDS!!!

@SuppressWarnings("serial")
public class CM {

public static class FightSettings implements Attribute<com.ashihara.datamanagement.pojo.FightSettings> { 

	private String parentPath;

	public FightSettings(){ this(null);}
	public FightSettings(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "FightSettings" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<String> getTatami() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "tatami";}}; }
	public Attribute<Long> getFirstRoundTime() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "firstRoundTime";}}; }
	public Attribute<Long> getSecondRoundTime() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "secondRoundTime";}}; }
	public Attribute<Long> getOtherRoundTime() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "otherRoundTime";}}; }
	public Attribute<Long> getTimeForRound() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "timeForRound";}}; }
	public Attribute<Long> getForDraw() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "forDraw";}}; }
	public Attribute<Long> getForWinning() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "forWinning";}}; }
	public Attribute<Long> getForLoosing() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "forLoosing";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightSettings.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class FightingGroup implements Attribute<com.ashihara.datamanagement.pojo.FightingGroup> { 

	private String parentPath;

	public FightingGroup(){ this(null);}
	public FightingGroup(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "FightingGroup" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<String> getName() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "name";}}; }
	public Attribute<String> getType() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "type";}}; }
	public Attribute<String> getStatus() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "status";}}; }
	public Attribute<String> getTatami() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "tatami";}}; }
	public YearWeightCategoryLink getYearWeightCategoryLink() {return new YearWeightCategoryLink(this.getAttributePath()+".yearWeightCategoryLink"); }
	public Attribute<String> getGender() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "gender";}}; }
	public Championship getChampionship() {return new Championship(this.getAttributePath()+".championship"); }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightingGroup.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class GroupChampionshipFighter implements Attribute<com.ashihara.datamanagement.pojo.GroupChampionshipFighter> { 

	private String parentPath;

	public GroupChampionshipFighter(){ this(null);}
	public GroupChampionshipFighter(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "GroupChampionshipFighter" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public FightingGroup getFightingGroup() {return new FightingGroup(this.getAttributePath()+".fightingGroup"); }
	public ChampionshipFighter getChampionshipFighter() {return new ChampionshipFighter(this.getAttributePath()+".championshipFighter"); }
	public Attribute<Long> getOlympicLevel() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return GroupChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "olympicLevel";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return GroupChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return GroupChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return GroupChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return GroupChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return GroupChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class FighterPhoto implements Attribute<com.ashihara.datamanagement.pojo.FighterPhoto> { 

	private String parentPath;

	public FighterPhoto(){ this(null);}
	public FighterPhoto(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "FighterPhoto" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Fighter getFighter() {return new Fighter(this.getAttributePath()+".fighter"); }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FighterPhoto.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return FighterPhoto.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return FighterPhoto.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FighterPhoto.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FighterPhoto.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class YearWeightCategoryLink implements Attribute<com.ashihara.datamanagement.pojo.YearWeightCategoryLink> { 

	private String parentPath;

	public YearWeightCategoryLink(){ this(null);}
	public YearWeightCategoryLink(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "YearWeightCategoryLink" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public YearCategory getYearCategory() {return new YearCategory(this.getAttributePath()+".yearCategory"); }
	public WeightCategory getWeightCategory() {return new WeightCategory(this.getAttributePath()+".weightCategory"); }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return YearWeightCategoryLink.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return YearWeightCategoryLink.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return YearWeightCategoryLink.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return YearWeightCategoryLink.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return YearWeightCategoryLink.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class Country implements Attribute<com.ashihara.datamanagement.pojo.Country> { 

	private String parentPath;

	public Country(){ this(null);}
	public Country(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "Country" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<String> getName() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Country.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "name";}}; }
	public Attribute<String> getCode() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Country.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "code";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return Country.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return Country.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return Country.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Country.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Country.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class WeightCategory implements Attribute<com.ashihara.datamanagement.pojo.WeightCategory> { 

	private String parentPath;

	public WeightCategory(){ this(null);}
	public WeightCategory(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "WeightCategory" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<Double> getToWeight() {return new Attribute<Double>() {@Override
	public String getAttributePath(){return WeightCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "toWeight";}}; }
	public Attribute<Double> getFromWeight() {return new Attribute<Double>() {@Override
	public String getAttributePath(){return WeightCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "fromWeight";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return WeightCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return WeightCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return WeightCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return WeightCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return WeightCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class User implements Attribute<com.ashihara.datamanagement.pojo.User> { 

	private String parentPath;

	public User(){ this(null);}
	public User(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "User" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<String> getName() {return new Attribute<String>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "name";}}; }
	public Attribute<String> getRole() {return new Attribute<String>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "role";}}; }
	public Attribute<String> getLookAndFeel() {return new Attribute<String>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lookAndFeel";}}; }
	public Attribute<String> getUiLanguage() {return new Attribute<String>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "uiLanguage";}}; }
	public Attribute<Long> getPassword() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "password";}}; }
	public Attribute<String> getTheme() {return new Attribute<String>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "theme";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return User.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class YearCategory implements Attribute<com.ashihara.datamanagement.pojo.YearCategory> { 

	private String parentPath;

	public YearCategory(){ this(null);}
	public YearCategory(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "YearCategory" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<Long> getFromYear() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "fromYear";}}; }
	public Attribute<Long> getToYear() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "toYear";}}; }
	public Attribute<List> getWeightCategories() {return new Attribute<List>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "weightCategories";}}; }
	public Attribute<List> getYearWeightCategories() {return new Attribute<List>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "yearWeightCategories";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return YearCategory.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class ChampionshipFighter implements Attribute<com.ashihara.datamanagement.pojo.ChampionshipFighter> { 

	private String parentPath;

	public ChampionshipFighter(){ this(null);}
	public ChampionshipFighter(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "ChampionshipFighter" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<Long> getNumber() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return ChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "number";}}; }
	public Championship getChampionship() {return new Championship(this.getAttributePath()+".championship"); }
	public Fighter getFighter() {return new Fighter(this.getAttributePath()+".fighter"); }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return ChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return ChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return ChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ChampionshipFighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class Fighter implements Attribute<com.ashihara.datamanagement.pojo.Fighter> { 

	private String parentPath;

	public Fighter(){ this(null);}
	public Fighter(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "Fighter" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<String> getName() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "name";}}; }
	public Country getCountry() {return new Country(this.getAttributePath()+".country"); }
	public Attribute<Long> getFullYearsOld() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "fullYearsOld";}}; }
	public Attribute<Date> getBirthday() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "birthday";}}; }
	public Attribute<String> getSurname() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "surname";}}; }
	public Attribute<Double> getWeight() {return new Attribute<Double>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "weight";}}; }
	public Attribute<Long> getKyu() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "kyu";}}; }
	public Attribute<Long> getDan() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "dan";}}; }
	public Attribute<String> getGender() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "gender";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Fighter.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class FightResult implements Attribute<com.ashihara.datamanagement.pojo.FightResult> { 

	private String parentPath;

	public FightResult(){ this(null);}
	public FightResult(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "FightResult" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<List> getChildren() {return new Attribute<List>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "children";}}; }
	public Attribute<Long> getSecondFighterSecondCategoryWarnings() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "secondFighterSecondCategoryWarnings";}}; }
	public Attribute<Long> getOlympicLevel() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "olympicLevel";}}; }
	public GroupChampionshipFighter getFirstFighter() {return new GroupChampionshipFighter(this.getAttributePath()+".firstFighter"); }
	public GroupChampionshipFighter getSecondFighter() {return new GroupChampionshipFighter(this.getAttributePath()+".secondFighter"); }
	public Attribute<Long> getFirstFighterPoints() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "firstFighterPoints";}}; }
	public Attribute<Long> getSecondFighterPoints() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "secondFighterPoints";}}; }
	public GroupChampionshipFighter getRedFighter() {return new GroupChampionshipFighter(this.getAttributePath()+".redFighter"); }
	public GroupChampionshipFighter getBlueFighter() {return new GroupChampionshipFighter(this.getAttributePath()+".blueFighter"); }
	public FightResult getPreviousRoundFightResult() {return new FightResult(this.getAttributePath()+".previousRoundFightResult"); }
	public Attribute<Long> getFirstFighterFirstCategoryWarnings() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "firstFighterFirstCategoryWarnings";}}; }
	public Attribute<Long> getFirstFighterSecondCategoryWarnings() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "firstFighterSecondCategoryWarnings";}}; }
	public Attribute<Long> getSecondFighterFirstCategoryWarnings() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "secondFighterFirstCategoryWarnings";}}; }
	public FightResult getNextRoundFightResult() {return new FightResult(this.getAttributePath()+".nextRoundFightResult"); }
	public Attribute<Long> getRoundNumber() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "roundNumber";}}; }
	public Attribute<Long> getFirstFighterPointsForWin() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "firstFighterPointsForWin";}}; }
	public Attribute<Long> getSecondFighterPointsForWin() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "secondFighterPointsForWin";}}; }
	public Attribute<Long> getOlympicPositionOnLevel() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "olympicPositionOnLevel";}}; }
	public FightResult getFirstFighterParent() {return new FightResult(this.getAttributePath()+".firstFighterParent"); }
	public FightResult getSecondFighterParent() {return new FightResult(this.getAttributePath()+".secondFighterParent"); }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
	public Attribute<String> getFirstFighterWinByJudgeDecision() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "firstFighterWinByJudgeDecision";}}; }
	public Attribute<String> getSecondFighterWinByJudgeDecision() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "secondFighterWinByJudgeDecision";}}; }
	public Attribute<String> getFirstFighterWinByTKO() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "firstFighterWinByTKO";}}; }
	public Attribute<String> getSecondFighterWinByTKO() {return new Attribute<String>() {@Override
	public String getAttributePath(){return FightResult.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "secondFighterWinByTKO";}}; }
} 

public static class ExceptionStackTraceDo implements Attribute<com.ashihara.datamanagement.pojo.ExceptionStackTraceDo> { 

	private String parentPath;

	public ExceptionStackTraceDo(){ this(null);}
	public ExceptionStackTraceDo(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "ExceptionStackTraceDo" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public ExceptionHeaderDo getExceptionHeaderDo() {return new ExceptionHeaderDo(this.getAttributePath()+".exceptionHeaderDo"); }
	public Attribute<String> getOneTraceLine() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ExceptionStackTraceDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "oneTraceLine";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return ExceptionStackTraceDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return ExceptionStackTraceDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return ExceptionStackTraceDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ExceptionStackTraceDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ExceptionStackTraceDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class Championship implements Attribute<com.ashihara.datamanagement.pojo.Championship> { 

	private String parentPath;

	public Championship(){ this(null);}
	public Championship(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "Championship" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<String> getName() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Championship.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "name";}}; }
	public Attribute<String> getRules() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Championship.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "rules";}}; }
	public Attribute<Date> getBeginningDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return Championship.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "beginningDate";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return Championship.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return Championship.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return Championship.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Championship.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return Championship.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class ExceptionHeaderDo implements Attribute<com.ashihara.datamanagement.pojo.ExceptionHeaderDo> { 

	private String parentPath;

	public ExceptionHeaderDo(){ this(null);}
	public ExceptionHeaderDo(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "ExceptionHeaderDo" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<String> getDescription() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ExceptionHeaderDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "description";}}; }
	public Attribute<String> getHeaderCause() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ExceptionHeaderDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "headerCause";}}; }
	public Attribute<Long> getId() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return ExceptionHeaderDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "id";}}; }
	public Attribute<Date> getLastModifiedDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return ExceptionHeaderDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedDate";}}; }
	public Attribute<Date> getCreationDate() {return new Attribute<Date>() {@Override
	public String getAttributePath(){return ExceptionHeaderDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "creationDate";}}; }
	public Attribute<String> getCreatedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ExceptionHeaderDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "createdUserId";}}; }
	public Attribute<String> getLastModifiedUserId() {return new Attribute<String>() {@Override
	public String getAttributePath(){return ExceptionHeaderDo.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "lastModifiedUserId";}}; }
} 

public static class FighterPlace implements Attribute<com.ashihara.datamanagement.pojo.wraper.FighterPlace> { 

	private String parentPath;

	public FighterPlace(){ this(null);}
	public FighterPlace(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "FighterPlace" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<Long> getPoints() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FighterPlace.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "points";}}; }
	public Attribute<Long> getFirstCategoryWarnings() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FighterPlace.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "firstCategoryWarnings";}}; }
	public Attribute<Long> getSecondCategoryWarnings() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FighterPlace.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "secondCategoryWarnings";}}; }
	public Attribute<Long> getPointsForWin() {return new Attribute<Long>() {@Override
	public String getAttributePath(){return FighterPlace.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "pointsForWin";}}; }
	public GroupChampionshipFighter getGCFighter() {return new GroupChampionshipFighter(this.getAttributePath()+".gCFighter"); }
	public Attribute<Integer> getPlace() {return new Attribute<Integer>() {@Override
	public String getAttributePath(){return FighterPlace.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "place";}}; }
	public Attribute<Integer> getWonByJudgeDecisionCount() {return new Attribute<Integer>() {@Override
	public String getAttributePath(){return FighterPlace.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "wonByJudgeDecisionCount";}}; }
	public Attribute<Integer> getWonByTKOCount() {return new Attribute<Integer>() {@Override
	public String getAttributePath(){return FighterPlace.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "wonByTKOCount";}}; }
	
} 

public static class FightResultReport implements Attribute<com.ashihara.datamanagement.pojo.wraper.FightResultReport> { 

	private String parentPath;

	public FightResultReport(){ this(null);}
	public FightResultReport(String parentPath){ super(); this.parentPath = parentPath;}

	@Override
	public String getAttributePath() { return parentPath == null ? getAttributeName() : parentPath;}
	@Override
	public String getAttributeName() { return parentPath == null ? "FightResultReport" : parentPath.substring(parentPath.lastIndexOf(".") + 1); }

	public Attribute<List> getPreviousRounds() {return new Attribute<List>() {@Override
	public String getAttributePath(){return FightResultReport.this.getAttributePath()+"."+getAttributeName();}@Override
	public String getAttributeName(){return "previousRounds";}}; }
	public FightResult getLastRound() {return new FightResult(this.getAttributePath()+".lastRound"); }
} 

}