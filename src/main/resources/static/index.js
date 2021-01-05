'use strict';

function getManagerDescription(title) {
    return '<td>' + title.firstName + '</td><td>' + title.lastName + '</td><td>' + title.fromDate + ' - ' + title.toDate + '</td>';
}

function getEmployeeRow(employee) {
    return '<td>' + employee.firstName + '</td><td>' + employee.lastName + '</td><td>' + employee.hireDate + '</td>';
}

function getRecentEmployees() {
    $.get("/api/employees/recent/")
        .done(function(employees) {
            $('#getRecentEmployeesResult').html('Found: <table>' +
                '<tr><th>First name</th><th>Last name</th><th>Hire date</th></tr>' +
                employees.map(employee => '<tr>' + getEmployeeRow(employee) + '</tr>').join('\n') +
                '</table>'
            );
        });
}

function getManagers() {
    $.get("/api/titles/manager/")
        .done(function(titles) {
            $('#getManagersResult').html('Found: <table>' +
                '<tr><th>First name</th><th>Last name</th><th>Period of managerial position</th></tr>' +
                titles.map(title => '<tr>' + getManagerDescription(title) + '</tr>').join('\n') +
                '</table>'
            );
        });
}

function countEmployees() {
    $.get("/api/employees/since1990/count/")
        .done(function (number) {
            $('#countEmployeesResult').html('Found ' + number + ' employees');
        })
}

function groupLayoff() {
    const title = $('#titleToLayoffSelect').val();
    $.post("/api/title/" + title + "/groupLayoff/")
        .done(function () {
            $('#layoffResult').show();
        })
}

function hireBack() {
    const title = $('#titleToLayoffSelect').val();
    $.post("/api/title/" + title + "/hireBack/")
        .done(function () {
            $('#hireBackResult').show();
        })
}

function getSalaryHistory() {
    $.get("/api/employees/recent/salaryhistory/")
        .done(function(data) {
            $('#salaryHistoryResult').html(
                data.map(
                    employee => getEmployeeSalariesDescription(employee)
                ).join('\n')
            );
        });
}

function getEmployeeSalariesDescription(employee) {
    return employee.firstName + ' ' + employee.lastName + '<table>' +
        '<tr><th>From data</th><th>To date</th><th>Salary</th></tr>' +
        employee.salaries.map(salary => '<tr><td>' + salary.fromDate + '</td><td>' + salary.toDate + '</td><td>' + salary.salary + '</td></tr>').join('\n') +
        '</table>';
}

function riseSalary() {
    $.post("/api/employee/10076/salary/rise10p/")
        .always(function () {
            $('#salaryRiseResult').show();
        })
}

function checkSalary() {
    $.get("/api/employee/10076/salary/")
        .always(function (salary) {
            $('#salaryCheckResult').html("Current salary is " + salary.salary + ".");
        })
}

function checkAvgSalary() {
    $.get("/api/titles/assistantengineer/avgsalary/")
        .always(function (salary) {
            $('#salaryAvgCheckResult').html("Current average salary is " + salary.salary + ".");
        })
}

function addLinkListeners() {
    $('#getRecentEmployeesLink').click(getRecentEmployees);
    $('#getManagersLink').click(getManagers);
    $('#countEmployeesLink').click(countEmployees);
    $('#layoffBtn').click(groupLayoff);
    $('#hireBackBtn').click(hireBack);
    $('#salaryHistoryBtn').click(getSalaryHistory);
    $('#salaryCheckBtn').click(checkSalary);
    $('#salaryRiseBtn').click(riseSalary);
    $('#salaryAvgCheckBtn').click(checkAvgSalary);
}

$( document ).ready(function() {
    addLinkListeners();
});