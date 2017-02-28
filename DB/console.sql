
select * from cib_cust_deposit;

insert into cib_cust_deposit values('1','1','1','1','1','1','1','1');

truncate table cib_cust_deposit;

alter table cib_cust_deposit add depositMonth VARCHAR(100);

-- 企业无贷户
select * from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd ='0' ;
select jgmc,sum(cast(nrj as FLOAT)) from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd ='0'  group by jgmc order by jgmc DESC ;
-- 企业有贷户
select * from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd not in ('0');
select jgmc,sum(cast(nrj as FLOAT)) from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd not in ('0')  group by jgmc;
-- 机构无贷忽
select jgmc,sum(cast(nrj as FLOAT)) from cib_cust_deposit where jglb not in ('01','02','13','14') and sfyd ='0'  group by jgmc;
-- 机构有贷户
select jgmc,sum(cast(nrj as FLOAT)) from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd not in ('0')  group by jgmc;
