package org.eu.sdsz.hanamaru.saucenao.data

data class DBMask(var value: Long = 0) {
    fun toggle(db : DB) {
        value = value xor db.mask
    }
    fun add(db: DB) {
        value = value or db.mask
    }
    fun remove(db: DB) {
        value = value and db.mask.inv()
    }

    constructor(dbs: List<DB>) : this() {
        for (db in dbs) {
            add(db)
        }
    }

    enum class DB(val mask: Long, val index: Int, val siteName: String) {
        H_MAGS             (          0x1,  0, "H-Magazines"),
        // H_ANIME(0x2, 1),
        HCG                (          0x4,  2, "H-Game CG"),
        // DDB_OBJECTS(0x8, 3, "DoujinshiDB"),
        // DDB_SAMPLES(0x10, 4),
        PIXIV              (         0x20,  5, "Pixiv Images"),
        PIXIVHISTORICAL    (         0x40,  6, "Pixiv Historical"),
        //        ANIME(0x80, 7),
        NICO_NICO_SEIGA    (        0x100,  8, "Nico Nico Seiga"),
        DANBOORU           (        0x200,  9, "Danbooru"),
        DRAWR              (        0x400, 10, "Drawr Images"),
        NIJIE              (        0x800, 11, "Nijie Images"),
        YANDERE            (       0x1000, 12, "Yande.re"),
        // ANIMEOP(0x2000, 13),
        // IMDB(0x4000, 14),
        // SHUTTERSTROCK(0x8000, 15, "Shutterstock"),
        FAKKU              (      0x10000, 16, "FAKKU"),
        H_MISC_NHENTAI     (      0x20000, 18, "H-Misc (nH)"),
        TWO_D_MARKET       (      0x40000, 19, "2D-Market"),
        MEDIABANG          (      0x80000, 20, "MediBang"),
        ANIME              (     0x100000, 21, "Anime"),
        H_ANIME            (     0x200000, 22, "H-Anime"),
        MOVIES             (     0x400000, 23, "Movies"),
        SHOWS              (     0x800000, 24, "Shows"),
        GELBOORU           (    0x1000000, 25, "Gelbooru"),
        KONACHAN           (    0x2000000, 26, "Konachan"),
        SANKAKU            (    0x4000000, 27, "Sankaku Channel"),
        ANIME_PICTURES     (    0x8000000, 28, "Anime-Pictures.net"),
        E621               (   0x10000000, 29, "e621.net"),
        IDOL_COMPLEX       (   0x20000000, 30, "Idol Complex"),
        BCY_ILLUST         (   0x40000000, 31, "bcy.net Illust"),
        BCY_COSPLAY        (   0x80000000, 32, "bcy.net Cosplay"),
        PROTALGRAPHICS     (  0x100000000, 33, "PortalGraphics.net"),
        DA                 (  0x200000000, 34, "deviantArt"),
        PAWOO              (  0x400000000, 35, "Pawoo.net"),
        MADOKAMI           (  0x800000000, 36, "Madokami (Manga)"),
        MANGADEX           ( 0x1000000000, 37, "MangaDex"),
        H_MISC_EHENTAI     ( 0x2000000000, 38, "H-Misc (eH)"),
        ARTSTATION         ( 0x4000000000, 39, "ArtStation"),
        FURAFFINITY        ( 0x8000000000, 40, "FurAffinity"),
        TWITTER            (0x10000000000, 41, "Twitter"),
        FURRY_NETWORK      (0x20000000000, 42, "Furry Network"),
        KEMONO             (0x40000000000, 43, "Kemono"),
        SKEB               (0x80000000000, 44, "Skeb")
    }
}