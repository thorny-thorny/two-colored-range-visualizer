import me.thorny.twoColoredRange.TwoColoredIntArrayRange

enum class DownloadState {
  Waiting,
  Downloaded,
}

class DownloadAtlas(size: Int): TwoColoredIntArrayRange<DownloadState>(
  1..size,
  DownloadState.Waiting,
  DownloadState.Downloaded
) {
  fun setRandomAwaitingSubrangeDownloaded(maxLength: Int): Boolean {
    val waitingSubranges = getSubrangesOfColor(DownloadState.Waiting)
    val randomWaitingSubrange = waitingSubranges.randomOrNull() ?: return false

    val start = (randomWaitingSubrange.start..randomWaitingSubrange.endInclusive).random()
    val endInclusive = (start..minOf(start + maxLength - 1, randomWaitingSubrange.endInclusive)).random()
    setSubrangeColor(start..endInclusive, DownloadState.Downloaded)
    return true
  }

  fun clear() {
    setSubrangeColor(range, DownloadState.Waiting)
  }

  fun hasWaitingSubranges(): Boolean {
    return getSubrangesOfDefaultColor().isNotEmpty()
  }
}
