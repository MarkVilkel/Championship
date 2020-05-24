alter table championship_fighter
drop constraint FK14B9883484EAD99;
alter table championship_fighter
add CONSTRAINT FK14B9883484EAD99 FOREIGN KEY (championship)
REFERENCES championship (id) 
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fight_result
drop constraint FK4A9B3B4C66EE009A;
alter table fight_result
add CONSTRAINT FK4A9B3B4C66EE009A FOREIGN KEY (second_fighter)
REFERENCES group_championship_fighter (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fight_result
drop constraint FK4A9B3B4CD55D92D6;
alter table fight_result
add CONSTRAINT FK4A9B3B4CD55D92D6 FOREIGN KEY (first_fighter)
REFERENCES group_championship_fighter (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fight_result
drop constraint FK4A9B3B4C88A0BDA3;
alter table fight_result
add CONSTRAINT FK4A9B3B4C88A0BDA3 FOREIGN KEY (PREVIOUS_ROUND_FIGHT_RESULT)
REFERENCES fight_result (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fight_result
drop constraint FK4A9B3B4C91C79427;
alter table fight_result
add CONSTRAINT FK4A9B3B4C91C79427 FOREIGN KEY (NEXT_ROUND_FIGHT_RESULT)
REFERENCES fight_result (id)
ON UPDATE NO ACTION ON DELETE SET NULL;


alter table fighter_photo
drop constraint FKA81B7BF0AB6162CB;
alter table fighter_photo
add CONSTRAINT FKA81B7BF0AB6162CB FOREIGN KEY (fighter)
REFERENCES fighter (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fighting_group
drop constraint FK30D761B2484EAD99;
alter table fighting_group
add CONSTRAINT FK30D761B2484EAD99 FOREIGN KEY (championship)
REFERENCES championship (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table group_championship_fighter
drop constraint FKF98AB663ABC774C;
alter table group_championship_fighter
add CONSTRAINT FKF98AB663ABC774C FOREIGN KEY (championship_fighter)
REFERENCES championship_fighter (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table group_championship_fighter
drop constraint FKF98AB663E09A8E30;
alter table group_championship_fighter
add CONSTRAINT FKF98AB663E09A8E30 FOREIGN KEY (fighting_group)
REFERENCES fighting_group (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table exception_stack_trace_do
drop constraint FK66338BECE16F1D05;
alter table exception_stack_trace_do
ADD CONSTRAINT FK66338BECE16F1D05 FOREIGN KEY (exception_header_do)
REFERENCES exception_header_do (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table year_weight_category_link
drop CONSTRAINT FK9D0C8BD6270E206A;
alter table year_weight_category_link 
add CONSTRAINT FK9D0C8BD6270E206A FOREIGN KEY (year_category)
REFERENCES year_category (id)
ON UPDATE NO ACTION ON DELETE CASCADE;


For Championship Plan

alter table CHAMPIONSHIP_PLAN
drop constraint FK56846103484EAD99;
alter table CHAMPIONSHIP_PLAN
add CONSTRAINT FK56846103484EAD99 FOREIGN KEY (championship)
REFERENCES championship (id)
ON UPDATE NO ACTION ON DELETE CASCADE;

alter table fighting_group
drop constraint FK30D761B22ECBDBE6;
alter table fighting_group
add CONSTRAINT FK30D761B22ECBDBE6 FOREIGN KEY (PLAN)
REFERENCES CHAMPIONSHIP_PLAN (id)
ON UPDATE NO ACTION ON DELETE CASCADE;