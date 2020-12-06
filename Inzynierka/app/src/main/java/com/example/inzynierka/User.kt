package com.example.inzynierka

class User {
    var id: String;
    var login: String;
    var password: String;
    var externalId: String;
    var adminUser: Boolean;

        constructor() {
            this.id = "";
            this.login = "";
            this.password = "";
            this.externalId = "";
            this.adminUser = false;
        }

}