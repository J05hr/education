--Joshua Rozenberg
--HW 3

--1.
CREATE DATABASE school;

CREATE TABLE students(
  sid int PRIMARY KEY NOT NULL,
  name varchar(255),
  age int,
  gpa float,
);

CREATE TABLE courses(
  cid varchar(7) PRIMARY KEY NOT NULL,
  deptid varchar(255),
  name varchar(255),
);

CREATE TABLE professors(
  ssn int PRIMARY KEY NOT NULL,
  name varchar(255),
  address varchar(255),
  phone varchar(255),
  deptid varchar(255)
);

CREATE TABLE enrollment(
  sid int NOT NULL,
  cid varchar(7),
  section int,
  grade varchar(2),
  PRIMARY KEY(sid, cid),
  FOREIGN KEY(sid) REFERENCES students,
  FOREIGN KEY(cid) REFERENCES courses,
  FOREIGN KEY(cid, section) REFRENCES teaches
);

CREATE TABLE teaches(
  cid varchar(7) NOT NULL
  section int,
  ssn int NOT NULL,
  PRIMARY KEY(cid, section),
  FOREIGN KEY(cid) REFERENCES courses,
  FOREIGN KEY(ssn) REFERENCES professors
);


-------------------------------------------------------------------------------
--2.
SELECT name
FROM professors
where deptid = 'cs';


-------------------------------------------------------------------------------
--3.
SELECT sid
FROM enrollment
WHERE cid IN (
  SELECT cid
  FROM courses
  WHERE deptid = 'cs');
--or
SELECT e.sid
FROM enrollment e, courses c
WHERE e.cid=c.cid AND c.deptid = 'cs';


-------------------------------------------------------------------------------
--4.
SELECT ssn, name
FROM professors
WHERE deptid = 'cs' AND
  ssn NOT IN (
    SELECT ssn
    FROM teaches
    WHERE cid IN (
        SELECT cid
        FROM courses
        WHERE deptid = 'cs');
    )
  );
--or
SELECT p.ssn, p.name
FROM professors p, course c
WHERE p.deptid = 'cs' AND p.cid = c.cid AND c.deptid != 'cs';


-------------------------------------------------------------------------------
--5.
SELECT COUNT(name), deptid
FROM courses
GROUP BY deptid;


-------------------------------------------------------------------------------
--6.
SELECT deptid
from courses
GROUP BY deptid
HAVING COUNT(name) > 10;


-------------------------------------------------------------------------------
--7.
SELECT DISTINCT s.name
FROM students s, enrollment e, teaches t, professors p
WHERE s.ssn = e.ssn AND
  e.cid = t.cid AND
  p.ssn = t.ssn AND
  t.name LIKE 'M%';


-------------------------------------------------------------------------------
--8.
SELECT c.deptid,
  SUM(if(Count(e.sid)<30),1,0) small,
  SUM(if(Count(e.sid)>=30 AND Count(e.sid)>80),1,0) medium,
  SUM(if(Count(e.sid)>=80),1,0) large,
FROM courses c, enrollment e
WHERE c.cid = e.cid
GROUP BY c.deptid;


-------------------------------------------------------------------------------
--9.
SELECT p.name
FROM professors p
WHERE p.deptid IN (
      SELECT p.deptid
      FROM professors p
      GROUP BY deptid HAVING COUNT(*) > 20)
  AND p.deptid IN (
      SELECT c.deptid,
        SUM(if(Count(e.sid)<30),1,0) small,
        SUM(if(Count(e.sid)>=30 AND Count(e.sid)>80),1,0) medium,
        SUM(if(Count(e.sid)>=80),1,0) large,
      FROM courses c, enrollment e
      WHERE c.cid = e.cid AND (small + medium) < (large)
      GROUP BY c.deptid);


-------------------------------------------------------------------------------
--10.
SELECT e.cid, e.section,
  100 * SUM(if(e.grade = 'F' OR e.grade = 'D',1,0))/COUNT(*) failure_rate
FROM enrollment e
GROUP BY e.cid, e.section;


-------------------------------------------------------------------------------
--11.
SELECT p.name
FROM (SELECT e.cid, e.section,
  100 * SUM(if(e.grade = 'F' OR e.grade = 'D',1,0))/COUNT(*) failure_rate
  FROM enrollment e, teaches t, professors p
  WHERE e.cid = t.cid AND p.ssn = t.ssn
  GROUP BY e.cid, e.section);


-------------------------------------------------------------------------------
--12.
SELECT 100 * SUM(if(e.grade = 'F' OR e.grade = 'D',1,0))/COUNT(*) failure_rate
FROM enrollment e;


-------------------------------------------------------------------------------
--13.
SELECT t1.cid, t1.section
FROM (SELECT e.cid, e.section,
  100 * SUM(if(e.grade = 'F' OR e.grade = 'D',1,0))/COUNT(*) failure_rate
  FROM enrollment e
  GROUP BY e.cid, e.section
) t1
WHERE t1.failure_rate > |(
  SELECT 100 * SUM(if(e.grade = 'F' OR e.grade = 'D',1,0))/COUNT(*) failure_rate
  FROM enrollment e);



-------------------------------------------------------------------------------
--14.
SELECT c.deptid,
  100*SUM(e.sid)/Count(*) SPS,
  100*SUM(if(e.grade = 'A'),1,0)/Count(*) A%,
  100*SUM(if(e.grade = 'B'),1,0)/Count(*) B%,
  100*SUM(if(e.grade = 'C'),1,0)/Count(*) C%,
  100*SUM(if(e.grade = 'D'),1,0)/Count(*) D%,
  100*SUM(if(e.grade = 'F'),1,0)/Count(*) F%,
FROM courses c, enrollment e
WHERE c.cid = e.cid
GROUP BY c.deptid;
