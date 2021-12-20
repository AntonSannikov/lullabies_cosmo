interface AdInterface {
    fun banner(isShowing: Boolean)
    fun checkPurchasesStatus()
    fun startPurchaseFlow(): Boolean
}