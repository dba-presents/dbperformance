'use strict';

function getManagerDescription(title) {
    return title.firstName + ' ' + title.lastName + ' ' + title.fromDate + ' - ' + title.toDate;
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
            $('#getManagersResult').html('Found: <ul>' +
                titles.map(title => '<li>' + getManagerDescription(title) + '</li>').join('\n') +
                '</ul>'
            );
        });
}

function addLinkListeners() {
    $('#getRecentEmployeesLink').click(getRecentEmployees);
    $('#getManagersLink').click(getManagers);
}

$( document ).ready(function() {
    addLinkListeners();
});