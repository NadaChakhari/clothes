package com.nada.clothes;


import java.io.Serializable;

public class User implements Serializable {


    private String nom;
        private String email;
        private String password;

        public User(){
            //this constructor is required
        }

        public User(String nom, String email, String password) {
            this.nom = nom;
            this.email = email;
            this.password = password;
        }

        public String getUserNom() {
            return nom;
        }

        public String getUserEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }


