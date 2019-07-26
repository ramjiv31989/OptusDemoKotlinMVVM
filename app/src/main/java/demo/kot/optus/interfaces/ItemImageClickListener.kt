package demo.kot.optus.interfaces

interface ItemImageClickListener {
    fun onItemClick(albumId: Int, photoId: Int, title: String, url: String)
}