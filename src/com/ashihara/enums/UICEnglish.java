package com.ashihara.enums;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class UICEnglish implements UIC {
	private static final long serialVersionUID = 1L;
	
	public final static String MONTHS[] = {
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "Oktober", "November", "December"
    };


	@Override
	public String CLOSE_ALL() {
		return "Close all";
	}

	@Override
	public String WINDOWS() {
		return "Windows";
	}

	@Override
	public String ASHIHARA_KARATE_CHAMPIONSHIP() {
		return "Josui Karate Championship";
	}

	@Override
	public String EXIT() {
		return "Exit";
	}

	@Override
	public String WAIT() {
		return "Wait";
	}

	@Override
	public String CHOICE() {
		return "Choice";
	}

	@Override
	public String ERROR() {
		return "Error";
	}

	@Override
	public String INFORMATION() {
		return "Infomartion";
	}

	@Override
	public String NO() {
		return "No";
	}

	@Override
	public String OK() {
		return "Ok";
	}

	@Override
	public String VALIDATION_ERROR() {
		return "Validation error";
	}

	@Override
	public String YES() {
		return "Yes";
	}

	@Override
	public String BACK() {
		return "Back";
	}

	@Override
	public String CANCEL() {
		return "Cancel";
	}

	@Override
	public String CLEAR() {
		return "Clear";
	}

	@Override
	public String CREATE_NEW_FOLDER() {
		return "Create new folder";
	}

	@Override
	public String DESKTOP() {
		return "Desktop";
	}

	@Override
	public String DETAILS() {
		return "Details";
	}

	@Override
	public String FILES_OF_TYPE() {
		return "File of type";
	}

	@Override
	public String FILE_NAME() {
		return "File name";
	}

	@Override
	public String LIST() {
		return "List";
	}

	@Override
	public String LOOK_IN_FOLDER() {
		return "Look in folder";
	}

	@Override
	public String OPEN() {
		return "Open";
	}

	@Override
	public String REFRESH() {
		return "Refresh";
	}

	@Override
	public String SAVE() {
		return "Save";
	}

	@Override
	public String SAVE_IN_FOLDER() {
		return "save in folder";
	}

	@Override
	public String SEARCH() {
		return "Search";
	}

	@Override
	public String TEXT_LENGTH_EXCEEDED_MAXIMAL_SYMBOLS_COUNT() {
		return "Text length exceeded maximal symbols count";
	}

	@Override
	public String UP_ONE_LEVEL() {
		return "Up one level";
	}

	@Override
	public String ATTACH() {
		return "Attach";
	}

	@Override
	public String CAN_NOT_SET_LOOK_AND_FEEL() {
		return "Can not set look and feel";
	}

	@Override
	public String CLOSE() {
		return "Close";
	}

	@Override
	public String DETACH() {
		return "Detach";
	}

	@Override
	public String EXPORT_TO_EXCEL_FILE() {
		return "Export to excel file";
	}

	@Override
	public String EXPORT_TO_FILE() {
		return "Expot to file";
	}

	@Override
	public String MAXIMIZE() {
		return "Mazimize";
	}

	@Override
	public String MINIMIZE() {
		return "Minimize";
	}

	@Override
	public String PUBLISH() {
		return "Publish";
	}

	@Override
	public String RESTORE() {
		return "Restore";
	}

	@Override
	public String TO_ARCHIVE() {
		return "Archive";
	}

	@Override
	public String UNPUBLISH() {
		return "Unpublish";
	}

	@Override
	public String UPLOAD() {
		return "Upload";
	}

	@Override
	public String CAN_NOT_CONNECT_TO_THE_DATA_BASE() {
		return "Can not connect to the data base";
	}

	@Override
	public String CHAMPIONSHIP() {
		return "Championship";
	}

	@Override
	public String CHAMPIONSHIP_PLAN() {
		return "Championship plan";
	}

	@Override
	public String PLAN() {
		return "Plan";
	}

	@Override
	public String FIGHTERS() {
		return "Fighters";
	}

	@Override
	public String PRESET() {
		return "Preset";
	}

	@Override
	public String EMAIL_HAS_WRONG_FORMAT() {
		return "E-mail address has wrong format!";
	}

	@Override
	public String FIELD_() {
		return "Field";
	}

	@Override
	public String FIELD_CAN_NOT_CONTAIN_SPACE_SYMBOLS() {
		return "field must not contain �space� symbol!";
	}

	@Override
	public String FIELD_DATA_TYPE_MUST_BE_INTEGER_NUMBER() {
		return "an integer number should be entered";
	}

	@Override
	public String FIELD_LENGTH_MUST_BE_LESS_THAN() {
		return "field length must not exceed";
	}

	@Override
	public String FIELD_LENGTH_MUST_BE_LESS_THAN_20_SYMBOLS() {
		return "field length must be less than 20 symbols!";
	}

	@Override
	public String FIELD_LENGTH_MUST_BE_LESS_THAN_255_SYMBOLS() {
		return "field length must be less than 255 symbols!";
	}

	@Override
	public String FIELD_MUST_BE_NOT_NEGATIVE() {
		return "field value can�t be negative!";
	}

	@Override
	public String FIGHTER() {
		return "Fighter";
	}

	@Override
	public String HAS_TO_HAVE_REAL_VALUE() {
		return "must contain a real value";
	}

	@Override
	public String IS_NOT_RECOGNIZABLE() {
		return "not recognizable";
	}

	@Override
	public String LENGTH_MUST_BE_GREATER_THAN() {
		return "length must be greater than";
	}

	@Override
	public String MUST_BE_DOUBLE() {
		return "must be real number!";
	}

	@Override
	public String MUST_BE_FILLED() {
		return "must be filled!";
	}

	@Override
	public String MUST_HIT_RANGE() {
		return "must be in a range:";
	}

	@Override
	public String NAME() {
		return "Name";
	}

	@Override
	public String SPECIFIED_SEARCH_CRITERIA() {
		return "Specified search criteria";
	}

	@Override
	public String SYMBOLS() {
		return "symbols";
	}

	@Override
	public String WRONG_DATE_FORMAT() {
		return "Wrong date format";
	}

	@Override
	public String CLICK_ONCE_TO_EXECUTE_LINK_ACTION() {
		return "Click once to go by this link!";
	}

	@Override
	public String CLICK_TWICE_TO_START_CELL_EDITTING() {
		return "Double-click or press F2 to edit the cell!";
	}

	@Override
	public String FORMAT_DATE(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH)+" "+ MONTHS[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
	}

	@Override
	public String H() {
		return "H";
	}

	@Override
	public String MIN() {
		return "Min";
	}

	@Override
	public String SEC() {
		return "Sec";
	}

	@Override
	public String ADD_NEW_ITEMS() {
		return "Add new items";
	}

	@Override
	public String ARE_YOU_SURE_YOU_WANT_TO_DELETE_SELECTED_ROWS() {
		return "Are you sure you want to delete selected rows?";
	}

	@Override
	public String BIRTHDAY() {
		return "Birthday";
	}

	@Override
	public String COUNTRY() {
		return "Country";
	}

	@Override
	public String DELETE_SELECTED_ROWS() {
		return "Delete selected rows";
	}

	@Override
	public String FIELD_DATA_TYPE_MUST_BE_REAL_NUMBER() {
		return "Field's data type must be real number";
	}

	@Override
	public String FIGHTERS_COUNT() {
		return "Fighters count";
	}

	@Override
	public String FIGHTER_INFO() {
		return "Fighter information";
	}

	@Override
	public String MUST_BE_THE_NUMBER() {
		return "Must be the number";
	}

	@Override
	public String NO_DATA_FOR_THIS_TABLE() {
		return "No data for this table";
	}

	@Override
	public String NUMBER() {
		return "Number";
	}

	@Override
	public String PLEASE_REFINE_YOUR_SEARCH_CRITERIA_AND_TRY_AGAIN() {
		return "Please define you search criteria and try again";
	}

	@Override
	public String PLEASE_SELECT_ANY_ROWS() {
		return "Please select any rows";
	}

	@Override
	public String SEARCH_BY_CRITERIA() {
		return "Search by criteria";
	}

	@Override
	public String SEARCH_CRITERIA_IS_ENABLED_SO_THERE_COULD_BE_SOME_MORE_ENTRIES() {
		return "Search criteria is enabled, so be adviced that the result set might be reduced";
	}

	@Override
	public String SPECIFY_THE_COUNT_OF_ITEMS_TO_BE_ADDED() {
		return "Specify the count of items to be added";
	}

	@Override
	public String SURNAME() {
		return "Surname";
	}

	@Override
	public String THE_NUMBER_MUST_BE_POSITIVE() {
		return "The number must be positive";
	}

	@Override
	public String WEIGHT() {
		return "Weight";
	}

	@Override
	public String CODE() {
		return "Code";
	}

	@Override
	public String COUNTRIES() {
		return "Countries";
	}

	@Override
	public String COUNTRIES_COUNT() {
		return "Countries count";
	}

	@Override
	public String COUNTRY_INFO() {
		return "Country information";
	}

	@Override
	public Locale GET_LOCALE() {
		return new Locale("En");
	}

	@Override
	public String LOAD_PHOTO() {
		return "Load photo";
	}

	@Override
	public String DELETE_PHOTO() {
		return "Delete photo";
	}

	@Override
	public String ALREADY_EXISTS() {
		return "already exits";
	}

	@Override
	public String DO_YOU_WANT_TO_OVERWRITE_IT() {
		return "Do you want to ovewrite it?";
	}

	@Override
	public String THE_FILE() {
		return "The file";
	}

	@Override
	public String UNSUPPORTED_FORMAT_OF_PHOTO() {
		return "Unsupported format of photo";
	}

	@Override
	public String ACTUAL_PHOTO_FILE_SIZE() {
		return "Actual photo file size";
	}
	
	@Override
	public String IS_BIGGER_THAN_ALLOWED() {
		return "is bigger than allowed";
	}

	@Override
	public String FROM_WEIGHT() {
		return "From weight (in kg)";
	}

	@Override
	public String FROM_WIEGHT_MUST_BE_LESS_THAN_TO_WEGHT() {
		return "'" + FROM_WEIGHT() + "' must be less than '" + TO_WEIGHT() + "'";
	}

	@Override
	public String TO_WEIGHT() {
		return "To weight (in kg)";
	}

	@Override
	public String WEIGHT_CATEGORIES_COUNT() {
		return "Count of weight categories";
	}

	@Override
	public String WEIGHT_CATEGORY_INFO() {
		return "Weight category info";
	}

	@Override
	public String WEIGHT_CATEGORIES() {
		return "Weight categories";
	}

	@Override
	public String WEIGHT_CATEGORY() {
		return "Weight category";
	}

	@Override
	public String FROM_WIEGHT_MUST_BE_POSITIVE() {
		return "'" + FROM_WEIGHT() + "' must be positive";
	}
	
	@Override
	public String TO_WIEGHT_MUST_BE_POSITIVE() {
		return "'" + TO_WEIGHT() + "' must be positive";
	}

	@Override
	public String FROM_YEAR() {
		return "From year";
	}

	@Override
	public String FROM_YEAR_MUST_BE_LESS_THAN_TO_YEAR() {
		return "'" + FROM_YEAR() + "' must be less than '" + TO_YEAR() + "'";
	}

	@Override
	public String FROM_YEAR_MUST_BE_POSITIVE() {
		return "'" + FROM_YEAR() + "' must be positive";
	}

	@Override
	public String TO_YEAR() {
		return "To year";
	}

	@Override
	public String TO_YEAR_MUST_BE_POSITIVE() {
		return "'" + TO_YEAR() + "' must be positive";
	}

	@Override
	public String YEAR_CATEGORIES() {
		return "Year categories";
	}

	@Override
	public String YEAR_CATEGORIES_COUNT() {
		return "Count of year categories";
	}

	@Override
	public String YEAR_CATEGORY() {
		return "Year category";
	}

	@Override
	public String YEAR_CATEGORY_INFO() {
		return "Year category information";
	}

	@Override
	public String CATEGORY() {
		return "Category";
	}

	@Override
	public String NEW_CHAMPIONSHIP() {
		return "New championship";
	}

	@Override
	public String SEARCH_CHAMPIONSHIP() {
		return "Search championship";
	}

	@Override
	public String INCLUDED_WEIGHT_CATEGORIES() {
		return "Included weight categories";
	}

	@Override
	public String SETUP_WEIGHT_CATEGORIES() {
		return "Setup weight categories";
	}

	@Override
	public String AT_LEAST_ONE_WEIGHT_CATEGORY_HAS_TO_BE_ADDED() {
		return "At least one weight category has to be added";
	}

	@Override
	public String VALUE_MUST_BE_UNIQUE() {
		return "value must be unique";
	}

	@Override
	public String COULD_NOT_DELETE_OBJECT() {
		return "Could not delete item";
	}

	@Override
	public String OTHER_ITEMS_IN_THE_SYSTEM_REFERENCES_ON_IT_DELETE_THEM_FIRST() {
		return "Other items in the system references on it, delete them first";
	}

	@Override
	public String COUNTRY_CODE_MUST_BE_UNIQUE() {
		return "Country code must be unique";
	}

	@Override
	public String COUNTRY_NAME_MUST_BE_UNIQUE() {
		return "Country name must be unique";
	}

	@Override
	public String DAN() {
		return "Dan";
	}

	@Override
	public String KYU() {
		return "Kyu";
	}

	@Override
	public String HAS_TO_HAVE_INTEGER_VALUE() {
		return "has to have integer value";
	}

	@Override
	public String OR() {
		return "or";
	}

	@Override
	public String SEARCH_FIGHTERS() {
		return "Search fighters";
	}

	@Override
	public String AGE() {
		return "Age";
	}

	@Override
	public String IS_LATER_THAN_TODAYS_DATE() {
		return "is later than todays date";
	}

	@Override
	public String LOOK_AND_FEEL() {
		return "Look&Feel";
	}

	@Override
	public String PREFERENCES() {
		return "Preferences";
	}

	@Override
	public String THEME() {
		return "Theme";
	}

	@Override
	public String BEGINNING_DATE() {
		return "Beginning date";
	}

	@Override
	public String TITLE() {
		return "Title";
	}

	@Override
	public String GROUPS_COUNT() {
		return "Groups count";
	}

	@Override
	public String CHAMPIONSHIP_INFO() {
		return "Championship information";
	}

	@Override
	public String TYPE() {
		return "Type";
	}

	@Override
	public String SELECT_FIGHTERS() {
		return "Select fighters";
	}

	@Override
	public String CHAMPIONSHIPS_COUNT() {
		return "Championships count";
	}

	@Override
	public String SEARCH_CHAMPIONSHIPS() {
		return "Search championships";
	}

	@Override
	public String SEARCH_YEAR_CATEGORIES() {
		return "Search year categories";
	}

	@Override
	public String BECAUSE_IT_DOES_NOT_CONTAIN_ANY_WEIGHT_CATEGORIES() {
		return "because it doesn't contain any weight categories";
	}

	@Override
	public String CAN_NOT_CREATE_GROUP_FOR_YEAR_CATEGORY() {
		return "Can not create group for year category";
	}

	@Override
	public String CUP() {
		return "Cup";
	}

	@Override
	public String ROUND_ROBIN() {
		return "Round Robin";
	}

	@Override
	public String SEARCH_WEIGHT_CATEGORIES() {
		return "Search weight categories";
	}

	@Override
	public String SELECT_WEIGHT_CATEGORIES() {
		return "Select weight categories";
	}

	@Override
	public String ADD_GROUP_BY_WEIGHT_CATEGORY() {
		return "By weight";
	}

	@Override
	public String ADD_YEAR_CATEGORY() {
		return "By year";
	}

	@Override
	public String SELECT_YEAR_CATEGORIES() {
		return "Select year categories";
	}

	@Override
	public String FILTER_GROUPS_BY_CRITERIA() {
		return "Filter groups by criteria";
	}

	@Override
	public String GROUP() {
		return "Group";
	}

	@Override
	public String GROUP_INFO() {
		return "Group information";
	}

	@Override
	public String GROUP_NAME() {
		return "Group name";
	}

	@Override
	public String FIGHTERS_COULD_NOT_BE_REPEATED() {
		return "Fighters in the tournament table could not be repeated";
	}

	@Override
	public String INITIAL() {
		return "initial";
	}

	@Override
	public String STARTED() {
		return "Started";
	}

	@Override
	public String START_TOURNAMENT() {
		return "Start tournament";
	}

	@Override
	public String FOR_DRAW() {
		return "For draw";
	}

	@Override
	public String FOR_LOOSING() {
		return "For loosing";
	}

	@Override
	public String FOR_WINNING() {
		return "For winning";
	}

	@Override
	public String POINTS() {
		return "Points";
	}

	@Override
	public String POINTS_INFO() {
		return "Points information";
	}

	@Override
	public String FIGHT() {
		return "Fight!";
	}

	@Override
	public String CHANGE_FIGHT_RESULT() {
		return "Change fight result";
	}

	@Override
	public String INVITE_TO_FIGHT() {
		return "Invite to fight";
	}

	@Override
	public String SELECT_ACTION() {
		return "Select action!";
	}

	@Override
	public String TATAMI() {
		return "Tatami";
	}

	@Override
	public String DRAW() {
		return "Draw";
	}

	@Override
	public String WON() {
		return "won";
	}

	@Override
	public String PLEASE_SELECT_THE_WINNER() {
		return "Please, select the winner";
	}

	@Override
	public String FIGHTER_MUST_BE_SELECTED() {
		return "Fighter must be selected";
	}

	@Override
	public String FILTER_FIGHTERS_BY_CRITERIA() {
		return "Filter fighters by criteria";
	}

	@Override
	public String RESULT_TABLE() {
		return "Results Table";
	}

	@Override
	public String FIGHTS() {
		return "Fights";
	}

	@Override
	public String PLACE() {
		return "Place";
	}

	@Override
	public String FIGHTERS_HAVE_THE_SAME_NUMBERS() {
		return "Fighters <{0}> and <{1}> have the same numbers <{2}>";
	}

	@Override
	public String HAS_TO_BE_POSITIVE_NUMBER() {
		return "has to be positive number!";
	}

	@Override
	public String IS_NOT_REAL_NUMBER() {
		return "is not real number!";
	}

	@Override
	public String GENDER() {
		return "Gender";
	}

	@Override
	public String FEMALE() {
		return "Female";
	}

	@Override
	public String MALE() {
		return "Male";
	}

	@Override
	public String IMPORT() {
		return "Import";
	}

	@Override
	public String FIRST_CATEGORY() {
		return "I category";
	}

	@Override
	public String SECOND_CATEGORY() {
		return "II category";
	}

	@Override
	public String WARNINGS() {
		return "Warnings";
	}

	@Override
	public String COUNTRY_NAME_AND_CODE_MUST_BE_UNIQUE() {
		return "Country name and code must be unique";
	}

	@Override
	public String NEXT_ROUND() {
		return "Next round";
	}

	@Override
	public String ROUND() {
		return "Round";
	}

	@Override
	public String ARE_YOU_SURE_YOU_WANT_TO_START_THE_NEXT_ROUND() {
		return "Are sure you want to start the NEXT ROUND?";
	}

	@Override
	public String RESULT_POINTS() {
		return "Result points";
	}

	@Override
	public String RESULT_SCORE() {
		return "Result Score";
	}

	@Override
	public String RESULT() {
		return "Result";
	}

	@Override
	public String REPORT() {
		return "Report";
	}

	@Override
	public String SHOW_REPORT() {
		return "Show report";
	}
	
	@Override
	public String FIRST_FIGHTER() {
		return "Fighter I";
	}

	@Override
	public String PREVIOUS_ROUNDS() {
		return "Previous Rounds";
	}

	@Override
	public String SCORE() {
		return "Score";
	}

	@Override
	public String SECOND_FIGHTER() {
		return "Fighter II";
	}

	@Override
	public String EACH_FIGHT_REPORT() {
		return "Each fight report";
	}

	@Override
	public String GROUP_PLACE_REPORT() {
		return "Group place report";
	}

	@Override
	public String PREVIOUS_ROUNDS_I() {
		return "Prev rounds I";
	}

	@Override
	public String PREVIOUS_ROUNDS_II() {
		return "Prev rounds II";
	}

	@Override
	public String FIGHTS_TABLE() {
		return "Fights table";
	}

	@Override
	public String ACTION() {
		return "Action";
	}

	@Override
	public String FIGHTS_COUNT() {
		return "Fights count";
	}

	@Override
	public String RESULTS_COUNT() {
		return "Results count";
	}

	@Override
	public String FIRST_ROUND_TIME_IN_SECONDS() {
		return "The first round time (seconds)";
	}

	@Override
	public String OTHER_ROUND_TIME_IN_SECONDS() {
		return "Other round time (seconds)";
	}

	@Override
	public String POINTS_FOR_DRAW() {
		return "Points for draw";
	}

	@Override
	public String POINTS_FOR_LOOSING() {
		return "Points for loosing";
	}

	@Override
	public String POINTS_FOR_WINNING() {
		return "Points for winning";
	}

	@Override
	public String SECOND_ROUND_TIME_IN_SECONDS() {
		return "The second round time (seconds)";
	}

	@Override
	public String FIGHT_SETTINGS() {
		return "Fight Settings";
	}

	@Override
	public String CAN_NOT_DELETE_FIGHTERS_GROUP_CAN_NOT_HAVE_LESS_THAN_2_FIGHTERS() {
		return "Can not delete fighters, group can not have less than 2 fighters";
	}

	@Override
	public String FIGHT_WINDOW_IS_ALREADY_OPENED_CLOSE_IT_FIRST() {
		return "Fight window is already opened, close it first!";
	}

	@Override
	public String CHECK_FIGHTERS() {
		return "Check fighters";
	}

	@Override
	public String NOBODY_IS_FORGOTTEN_ALL_FIGHTERS_IN_GROUPS() {
		return "Nobody is forgoten, all fighters in groups";
	}

	@Override
	public String SOME_FIGHTERS_ARE_NOT_IN_GROUPS() {
		return "Some fighters are not in groups";
	}

	@Override
	public String SWITCH_FIGHTERS() {
		return "Switch fighters";
	}

	@Override
	public String FILE() {
		return "File";
	}
	 
	@Override
	public String EXPORT() {
		return "Export";
	}

	@Override
	public String DATA_BASE() {
		return "Data Base";
	}

	@Override
	public String DATA_BASE_EXPORTED() {
		return "Data Base Exported";
	}

	@Override
	public String DATA_BASE_IMPORTED() {
		return "Data Base Imported";
	}

	@Override
	public String EXPORT_FINISHED_GROUPS() {
		return "Export finished groups";
	}

	@Override
	public String NO_FINISHED_GROUPS() {
		return "No finished groups";
	}

	@Override
	public String NEXT() {
		return "next";
	}

	@Override
	public String NEXT_FIGHT() {
		return "Next fight";
	}

	@Override
	public String VS() {
		return "VS";
	}

	@Override
	public String CAM_NOT_SET_NEXT_FIGHT_FOR() {
		return "Can not set next fight for";
	}

	@Override
	public String USER_NAME() {
		return "User name";
	}

	@Override
	public String PASSWORD() {
		return "Password";
	}

	@Override
	public String LANGUAGE() {
		return "Language";
	}

	@Override
	public String USER_NAME_AND_OR_PASSWORD_IS_INCORRECT() {
		return "User name/password is incorrect";
	}

	@Override
	public String AUTHENTIFICATION() {
		return "Authentification";
	}

	@Override
	public String MIXED() {
		return "Mixed";
	}

	@Override
	public String FIRST_COUNTRY() {
		return "Country I";
	}

	@Override
	public String SECOND_COUNTRY() {
		return "Country II";
	}

	@Override
	public String OLYMPIC_FIGH_RESULTS() {
		return "Olympic fight results";
	}

	@Override
	public String ALL() {
		return "All";
	}

	@Override
	public String OLYMPIC_TREE_FIGH_RESULTS() {
		return "Olympic tree fight results";
	}

	@Override
	public String EXPORT_OLYMPIC_TREE_TO_FILE() {
		return "Export olympic tree to file";
	}

	@Override
	public String EXPORT_CONTENT_TO_FILE() {
		return "Export content to file";
	}

	@Override
	public String JOSUI_STYLE() {
		return "Josui style";
	}

	@Override
	public String ALL_STYLE() {
		return "All style";
	}

	@Override
	public String RULES() {
		return "Rules";
	}

	@Override
	public String PENALTY() {
		return "Penalty";
	}

	@Override
	public String WIN_BY_JUDGE_DECISION() {
		return "Win by judge decision";
	}

	@Override
	public String BY_JUDGE_DECISION() {
		return "By judge decision";
	}

	@Override
	public String ASHIHARA() {
		return "Ashihara";
	}

	@Override
	public String WIN_BY_TKO() {
		return "Win by TKO";
	}

	@Override
	public String BY_TKO() {
		return "By TKO";
	}

	@Override
	public String SELECT_GROUPS() {
		return "Select groups";
	}

	@Override
	public String IN_PLAN() {
		return "In plan";
	}

	@Override
	public String PLAN_FOR() {
		return "Plan for";
	}

	@Override
	public String NR() {
		return "Nr";
	}

	@Override
	public String NO_NEXT() {
		return "no next";
	}

	@Override
	public String SHOW_NEXT_FIGHTS() {
		return "Show next fights";
	}

	@Override
	public String ARE_YOU_SURE_YOU_WANT_TO_START_THE_NEXT_FIGHT() {
		return "Are sure you want to start the NEXT FIGHT?";
	}

	@Override
	public String SKILL() {
		return "Skill";
	}

	@Override
	public String FINALS_AT_THE_END() {
		return "Finals at the end";
	}

	@Override
	public String FINALS() {
		return "Finals:";
	}

	@Override
	public String SEMI_FINALS() {
		return "Semi finals:";
	}

}
