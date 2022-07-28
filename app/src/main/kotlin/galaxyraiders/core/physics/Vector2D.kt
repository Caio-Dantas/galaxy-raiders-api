@file:Suppress("UNUSED_PARAMETER") // <- REMOVE

package galaxyraiders.core.physics

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.lang.Math.toDegrees
import kotlin.math.atan2
import kotlin.math.sqrt

@JsonIgnoreProperties("unit", "normal", "degree", "magnitude")
data class Vector2D(val dx: Double, val dy: Double) {
  override fun toString(): String {
    return "Vector2D(dx=$dx, dy=$dy)"
  }

  val magnitude: Double
    get() = sqrt(dx * dx + dy * dy)

  val radiant: Double
    get() = atan2(dy, dx)

  val degree: Double
    get() = toDegrees(radiant)

  val unit: Vector2D
    get() = this / magnitude

  val normal: Vector2D
    get() = Vector2D(dy, -dx).times(1 / magnitude)

  operator fun times(scalar: Double): Vector2D {
    return Vector2D(dx * scalar, dy * scalar)
  }

  operator fun div(scalar: Double): Vector2D {
    return Vector2D(dx / scalar, dy / scalar)
  }

  operator fun times(v: Vector2D): Double {
    return dx * v.dx + dy * v.dy
  }

  operator fun plus(v: Vector2D): Vector2D {
    return Vector2D(dx + v.dx, dy + v.dy)
  }

  operator fun plus(p: Point2D): Point2D {
    return p + this
  }

  operator fun unaryMinus(): Vector2D {
    return Vector2D(-dx, -dy)
  }

  operator fun minus(v: Vector2D): Vector2D {
    return this + (-v)
  }

  fun scalarProject(target: Vector2D): Double {
    return (this.times(target).div(target.magnitude))
  }

  fun vectorProject(target: Vector2D): Vector2D {
    return (target.div(target.magnitude)).times(scalarProject(target))
  }
}

operator fun Double.times(v: Vector2D): Vector2D {
  return v * this
}