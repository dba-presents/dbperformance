'use strict';

function getManagerDescription(title) {
    return title.firstName + ' ' + title.lastName + ' ' + title.fromDate + ' - ' + title.toDate;
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
    $('#getManagersLink').click(getManagers);
}

$( document ).ready(function() {
    addLinkListeners();
});