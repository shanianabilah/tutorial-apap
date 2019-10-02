# Tutorial APAP
## Authors
* **shania.nabilah** - *<1706044175>* - *C* <br>

###TUTORIAL 2
1. Error karena belum ada templates add-resto
2. Link berhasil dibuka karena sudah ada template add-resto
3. http://localhost:8080/restoran/view/idRestoran/1
4. Akan 2 restoran yang sudah ditambahkan
![Bukti](bukti.png)

###TUTORIAL 3
1. Pada class MenuDb, terdapat method findByRestoranIdRestoran, apakah kegunaan dari
   method tersebut?
   Untuk mendapatkan list menu berdasarkan id restorannya
2. Pada class RestoranController, jelaskan perbedaan method addRestoranFormPage dan
   addRestoranSubmit?
   Pada addRestoranFormPage menggunakan request method get, sedangkan pada addRestoranSubmit menggunakan request method post
3. Jelaskan apa kegunaan dari JPA Repository?
   Java Persistence API Repository berguna untuk persisting objek java ke database. JPA bisa dipakai untuk melakukan query, mengakses objek, dan melakukan define pada data
4. Sebutkan dan jelaskan di bagian kode mana sebuah relasi antara RestoranModel dan
   MenuModel dibuat?
   Di RestoranModel:
   @OneToMany(mappedBy = "restoran", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
       private List<MenuModel> listMenu;
   Di MenuModel:
   @ManyToOne(fetch = FetchType.EAGER, optional = false)
       @JoinColumn(name = "restoranId", referencedColumnName = "idRestoran", nullable = false)
       @OnDelete(action = OnDeleteAction.CASCADE)
       @JsonIgnore
       private RestoranModel restoran;
5. Jelaskan kegunaan FetchType.LAZY, CascadeType.ALL, dan FetchType.EAGER
    1. FetchType.LAZY digunakan ketika terdapat 2 entitas yang memiliki relasi. Dengan menggunakan fetch type lazy maka entitas tersebut akan mengambil atribut entitas lain apabila dibutuhkan
    2. CascadeType.ALL digunakan ketika terdapat relasi antar 2 entitas. Dengan menggunakan cascade type all maka apabila terdapat perubahan pada satu entitas akan mengubah entitas satunya lagi
    3. FetchType.EAGER digunakan ketika terdapat suatu entitas yang ingin diakses dari database oleh entitas lain maka seluruh atribut akan langsung di load tanpa suatu kondisi tertentu
   
