package com.BC181.Aryawan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_film";
    private final static String KEY_JUDUl = "Judul";
    private final static String KEY_TAHUN = "Tahun";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_PEMAIN = "Pemain";
    private final static String KEY_SIPNOSIS= "SIPNOSIS";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " +  TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUl + " TEXT, " + KEY_TAHUN + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_PEMAIN + " TEXT, " + KEY_SIPNOSIS + " TEXT);";
        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());

        db.insert(TABLE_FILM, null, cv);

    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }
                catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        //menambah data Film 1
        try{
            tempDate = sdFormat.parse("2017");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film1 = new Film(
                idFilm,
                "Money Heist",
                tempDate,
                storeImageFile(R.drawable.money),
                "Crime drama, Heist, Thriller",
                "Álvaro Morte\n" +
                        "Úrsula Corberó\n" +
                        "Pedro Alonso\n" +
                        "Itziar Ituño\n" +
                        "Najwa Nimri",
                "Berkisah tentang sekelompok perampok bank tersebut yang dipimpin oleh seseorang yang bernama \"Profesor\". Dia merencanakan perampokan ini dengan sangat teliti dan sangat terkesan melihat semuanya tersusun rapi. Walau jenius dalam merencanakan perampokan, dia juga sangat menentang yang namanya pembunuhan dalam perampokan itu. Para perampok ini memiliki nama-nama kota sebagai panggilannya, itu semua termasuk kedalam rencana \"Profesor\" dimana tidak boleh ada data pribadi yang diketahui oleh masing-masing perampok dan tidak boleh ada hubungan lebih dari sekedar \"rekan kerja\". Namun, konflik yang selalu datang bertubi-tubi dan datang dari segala arah, konflik ini bisa datang dari perampok, profesor, polisi, dan juga para sandera."
        );

        tambahFilm(film1, db);
        idFilm++;

        // Data Film ke 2
        try{
            tempDate = sdFormat.parse("2011");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film2 = new Film(
                idFilm,
                "Real Steel",
                tempDate,
                storeImageFile(R.drawable.real),
                "Action, Drama, Family",
                "Hugh Jackman sebagai Charlie Kenton\n" +
                        "Dakota Goyo sebagai Max Kenton\n" +
                        "Kevin Durand sebagai Ricky\n" +
                        "Evangeline Lilly sebagai Bailey Tallet",
                "Dikisahkan pada tahun 2020, olahraga tinju telah mengalami perubahan ke arah yang lebih modern. Di mana, keterbatasan manusia saling beradu di atas ring, mulai tergantikan oleh teknologi robot yang dikendalikan manusia melalui remote control.Para robotlah yang bertarung layaknya petinju. Kekerasan nyata tanpa kontrol. Saling membantai sampai hancur berkeping-keping. Itulah tujuan akhirnya. adegan pertarungan di real steelCerita Film diawali dari kehidupan Charlie Kenton (Hugh Jackman). Sang legenda tinju yang berjuang dari kesulitan hidup akibat hutang yang menumpuk karena selalu kalah dalam pertarungan tinju antar robot.\n" +
                        "\n" +
                        "The Ambush, robot terakhir sisa harapannya, harus kalah pada pertunjukan terakhir melawan banteng. Memaksanya untuk segera mencari pengganti sekaligus melunasi hutang-hutang.Kebetulan pada saat bersamaan di pengadilan, pemutusan hakim perkara hak asuh anak sedang dilaksanakan. Melihat hal itu, Charlie membuat perjanjian dengan ayah angkat Max, agar membayar 100.000 dollar.Dari situlah awal petualangan baru dimulai, antara ayah dan anak yang baru bertemu. Saling berdebat dan merasa paling benar. Charlie lagi-lagi harus menelan kekecewaan. Noisy Boy, robot baru yang dibeli dari uang perjanjian harus terbantai habis-habisan di atas ring. real steel sinopsis\n" +
                        "\n" +
                        "Karena sudah tidak ada peluang untuk memiliki robot baru, Charlie dan Max menuju ke tempat pembuangan. Karena ketidakhati-hatiannya, Max terjatuh dan beruntung tersangkut di sebuah rongsokan lengan robot. Sebuah robot yang akan menjadi tolak balik kehidupan keduanya.Dari sisa onderdil Ambush dan Noisy Boy, dengan bantuan Bailey seorang teknisi, Robot bernama ATOM dari Generesi kedua keluaran tahun 2014 berhasil dihidupkan kembali.Meskipun bertubuh kecil, Atom yang digerakkan berdasarkan respon proteksi dari gerakan Max dan Charlie mampu menghajar lawan-lawannya yang lebih besar. Akhirnya, taruhan demi taruhan berhasil dilalui, Atom semakin terkenal di kalangan pertandingan tinju antar robot.Sampai akhirnya, mereka menuju ke pentas pertandingan tinju bergengsi di dunia. Sebuah ajang pertarungan antar robot dengan teknologi terbaru di WRB" );
        tambahFilm(film2, db);
        idFilm++;

        // Tambah Film 3

        try{
            tempDate = sdFormat.parse("2009");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film3 = new Film(
                idFilm,
                "2012",
                tempDate,
                storeImageFile(R.drawable.filmtiga),
                "Action, Superhero",
                "John Cusack sebagai Curtis Jackson\n" +
                        "Amanda Peet sebagai Kate\n" +
                        "Liam James sebagai Noah Curtis,\n" +
                        "Morgan Lily sebagai Lilly Curtis,\n" +
                        "Thomas McCarthy sebagai Gordon,\n" +
                        "Danny Glover sebagai Thomas Wilson, \n" +
                        "Thandie Newton sebagai Laura Wilson,",
                "Dalam film ini, seorang geologis Amerika, Adrian Helmsley yang diperankan oleh Chiwetel Ejiofor bertemu dengan Satnam Tsurutani yang diperankan oleh Jimi Mistry di India.\n" +
                        "\n" +
                        "Ia merupakan ahli astrofisik yang mengklaim bahwa akan ada bencana besar yaitu badai matahari di tahun 2012 sehingga suhu Bumi naik drastic.\n" +
                        "\n" +
                        "Pernyataan bahwa akan badai ini pun sama dengan asumsi dari suku Maya. Hal ini membuat suku Maya melakukan bunuh diri massal. Pasalnya Kalender mereka berakhir pada 21 Desember 2012.\n" +
                        "\n" +
                        "Mengetahui apa yang terjadi, Presiden Amerika bersama dengan negara-negara besar lainnya mengadakan sebuah rapat rahasia. Dalam rapat tersebut, diputuskan bahwa akan dibentuk sebuah camp perlindungan dan memproduksi kapal-kapal yang dapat menampung 100.000 orang. Namun, karena keterbatasan dalam hal angkutan, orang yang diselamatkan akan dipilih.\n" +
                        "\n" +
                        "Beberapa tahun kemudian, tepatnya pada tahun 2012 seorang penulis fiksi yang juga memiliki pekerjaan sampingan sebagai supir, Jackson (John Cusack) bersama dengan kedua anaknya pergi ke wilayan Yellowstone.\n" +
                        "\n" +
                        "Dalam perjalanannya, mereka melewati area yang telah dipagari oleh pemerintah. Setelah melintasi are tersebut, mereka ditangkap oleh pihak keamanan. Mereka juga bertemu Charlie (Woody Harrelson), seorang penyiar yang pada saat itu membawakan berita mengenai prediksi suku Maya.\n" +
                        "\n" +
                        "Sejak saat itu, Jackson mulai melihat bencana itu. Berawal dari keretakan besar yang terjadi di Patahan San Andreas, California. Kemudian peristiwa lain pun ikut terjadi seperti gempa, hingga banjir bandang.");
        tambahFilm(film3, db);


    }

}