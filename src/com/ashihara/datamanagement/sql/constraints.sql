alter table championship_fighter
drop constraint fk14b9883484ead99;
alter table championship_fighter
add CONSTRAINT fk14b9883484ead99 FOREIGN KEY (championship)
REFERENCES championship (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fight_result
drop constraint fk4a9b3b4c66ee009a;
alter table fight_result
add CONSTRAINT fk4a9b3b4c66ee009a FOREIGN KEY (second_fighter)
REFERENCES group_championship_fighter (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fight_result
drop constraint fk4a9b3b4cd55d92d6;
alter table fight_result
add CONSTRAINT fk4a9b3b4cd55d92d6 FOREIGN KEY (first_fighter)
REFERENCES group_championship_fighter (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fighter_photo
drop constraint fka81b7bf0ab6162cb;
alter table fighter_photo
add CONSTRAINT fka81b7bf0ab6162cb FOREIGN KEY (fighter)
REFERENCES fighter (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fighting_group
drop constraint fk30d761b2484ead99;
alter table fighting_group
add CONSTRAINT fk30d761b2484ead99 FOREIGN KEY (championship)
REFERENCES championship (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table group_championship_fighter
drop constraint fkf98ab663abc774c;
alter table group_championship_fighter
add CONSTRAINT fkf98ab663abc774c FOREIGN KEY (championship_fighter)
REFERENCES championship_fighter (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table group_championship_fighter
drop constraint fkf98ab663e09a8e30;
alter table group_championship_fighter
add CONSTRAINT fkf98ab663e09a8e30 FOREIGN KEY (fighting_group)
REFERENCES fighting_group (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table exception_stack_trace_do
drop constraint fk66338bece16f1d05;
alter table exception_stack_trace_do
ADD CONSTRAINT fk66338bece16f1d05 FOREIGN KEY (exception_header_do)
REFERENCES exception_header_do (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table year_weight_category_link
drop CONSTRAINT fk9d0c8bd6270e206a;
alter table year_weight_category_link 
add CONSTRAINT fk9d0c8bd6270e206a FOREIGN KEY (year_category)
REFERENCES year_category (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;
