USE orangehrm_db;

select * from hs_hr_employee;
select * from ohrm_leave;
select * from ohrm_leave_status;
select * from ohrm_leave_type;
select * from ohrm_leave_request;

SELECT *
FROM hs_hr_employee e1  
	JOIN ohrm_leave_request lr1 ON e1.emp_number = lr1.emp_number  
        JOIN ohrm_leave l1 ON l1.leave_request_id = lr1.id  
        JOIN ohrm_leave_type lt1 ON l1.leave_type_id = lt1.id  
        JOIN ohrm_leave_status ls1 ON ls1.status = l1.status  
WHERE ls1.name IN ('TAKEN', 'SCHEDULED') AND YEAR(l1.date) = 2022
GROUP BY e1.emp_number  
HAVING SUM(l1.length_days) >= 0;
