package com.bansalankit.colormemory;

/**
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>04 Jul 2017</b></i>
 * <br><i>Modified Date : <b>04 Jul 2017</b></i>
 */
@Table(name = "Students")
public class Student extends DatabaseModel {
    @Column(name = "registrationId", primary = true, required = true)
    protected String mRegId;

    @Column(name = "name")
    private String mName;

    @Column(name = "email")
    private String mEmail;
}
