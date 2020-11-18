'use strict';

function getManagerDescription(title) {
    return title.firstName + ' ' + title.lastName + ' ' + title.fromDate + ' - ' + title.toDate;
}

function getEmployeeDescription(employee) {
    return employee.firstName + ' ' + employee.lastName + ' ' + employee.hireDate;
}

function getRecentEmployees() {
    $.get("/api/employees/recent/")
        .done(function(employees) {
            $('#getRecentEmployeesResult').html('Found: <ul>' +
                employees.map(employee => '<li>' + getEmployeeDescription(employee) + '</li>').join('\n') +
                '</ul>'
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