alter table `home` drop PRIMARY key;
alter table `home` add index `NameIndex` (`name`);
alter table `home` add index `WorldIndex` (`world`);