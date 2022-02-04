import me.thorny.twoColoredRange.TwoColoredIntArrayRange

enum class DownloadState {
  WAITING,
  DOWNLOADED,
}

class DownloadAtlas(size: Int): TwoColoredIntArrayRange<DownloadState>(
  1..size,
  DownloadState.WAITING,
  DownloadState.DOWNLOADED
) {
  fun setRandomAwaitingSubrangeDownloaded(maxLength: Int): Boolean {
    val waitingSubranges = getSubrangesOfColor(DownloadState.WAITING)
    val randomWaitingSubrange = waitingSubranges.randomOrNull() ?: return false

    val start = (randomWaitingSubrange.start..randomWaitingSubrange.endInclusive).random()
    val endInclusive = (start..minOf(start + maxLength - 1, randomWaitingSubrange.endInclusive)).random()
    setSubrangeColor(start..endInclusive, DownloadState.DOWNLOADED)
    return true
  }

  fun clear() {
    setSubrangeColor(range, DownloadState.WAITING)
  }

  fun hasWaitingSubranges(): Boolean {
    return getSubrangesOfDefaultColor().isNotEmpty()
  }
}
