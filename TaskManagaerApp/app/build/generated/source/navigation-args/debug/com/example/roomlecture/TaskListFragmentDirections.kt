package com.example.roomlecture

import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.Long

public class TaskListFragmentDirections private constructor() {
  private data class ActionTaskListFragmentToTaskDetailFragment(
    public val id: Long = 0L
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_taskListFragment_to_taskDetailFragment

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putLong("id", this.id)
      return result
    }
  }

  public companion object {
    public fun actionTaskListFragmentToTaskDetailFragment(id: Long = 0L): NavDirections =
        ActionTaskListFragmentToTaskDetailFragment(id)
  }
}
