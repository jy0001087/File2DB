
select count(*) from cib_cust_deposit;

select * from cib_cust_deposit WHERE depositmonth is not null;


truncate table cib_cust_deposit;

alter table cib_cust_deposit add depositMonth VARCHAR(100);

-- 企业无贷户
select * from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd ='0' ;


select jgmc,sum(cast(nrj as FLOAT)),depositmonth,deposittype from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd ='0'
  and jgmc like '%业务%'
group by jgmc,depositmonth,deposittype order by jgmc,depositmonth,deposittype DESC;

-- 企业有贷户
select jgmc,sum(cast(nrj as FLOAT)),depositmonth,deposittype from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd not in ('0')
and jgmc like '%业务%'
group by jgmc,depositmonth,deposittype order by jgmc,depositmonth,deposittype DESC;

-- 机构无贷忽
select jgmc,sum(cast(nrj as FLOAT)),depositmonth,deposittype from cib_cust_deposit where jglb not in ('01','02','13','14') and sfyd ='0'and jgmc like '%业务%'
group by jgmc,depositmonth,deposittype order by jgmc,depositmonth,deposittype DESC;

-- 机构有贷户
select jgmc,sum(cast(nrj as FLOAT)),depositmonth,deposittype from cib_cust_deposit where jglb in ('01','02','13','14') and sfyd not in ('0') and jgmc like '%业务%'
group by jgmc,depositmonth,deposittype order by jgmc,depositmonth,deposittype DESC;