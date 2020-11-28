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

function addLinkListeners() {
    $('#getRecentEmployeesLink').click(getRecentEmployees);
    $('#getManagersLink').click(getManagers);
    $('#countEmployeesLink').click(countEmployees);
}

$( document ).ready(function() {
    addLinkListeners();
});