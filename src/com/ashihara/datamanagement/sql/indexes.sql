create index INDEX_AK_USER
on AK_USER(name);

create index INDEX_CHAMPIONSHIP
on CHAMPIONSHIP(name);

create index INDEX_COUNTRY
on COUNTRY(name);

create index INDEX_EXCEPTION_STACK_TRACE_DO
on EXCEPTION_STACK_TRACE_DO(exception_header_do);

create index INDEX_EXCEPTION_HEADER_DO
on EXCEPTION_HEADER_DO(id);

create index INDEX_FIGHTER
on FIGHTER(name, surname, country);

create index INDEX_FIGHTING_GROUP
on FIGHTING_GROUP(championship);

create index INDEX_FIGHTER_PHOTO
on FIGHTER_PHOTO(fighter);

create index INDEX_WEIGHT_CATEGORY
on WEIGHT_CATEGORY(id);

create index INDEX_YEAR_CATEGORY
on YEAR_CATEGORY(id);

create index INDEX_YEAR_WEIGHT_CATEGORY_LINK
on YEAR_WEIGHT_CATEGORY_LINK(YEAR_CATEGORY);

create index INDEX_CHAMPIONSHIP_FIGHTER
on CHAMPIONSHIP_FIGHTER(championship, fighter);

create index INDEX_FIGHT_RESULT
on FIGHT_RESULT(first_fighter, second_fighter);

create index INDEX_GROUP_CHAMPIONSHIP_FIGHTER
on GROUP_CHAMPIONSHIP_FIGHTER(fighting_group, championship_fighter);

create index INDEX_POINTS
on POINTS(id);