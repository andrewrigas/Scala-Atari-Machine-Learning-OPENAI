package OpenAI.gym

import spray.json.{DefaultJsonProtocol, JsArray, RootJsonFormat}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

//Response Messages


//Non-general Responses
sealed trait Observation

sealed trait StepReply(reward: Double, done: Boolean, info: Map[String, Int])




//General Responses
case class EnvInstance(instance_id: String)

case class ListEnvs(all_envs: Map[String , String])

case class DiscreteSpace(name: String, n: Int)

case class ActionSpace(info:DiscreteSpace) {
  def sample(): Int = {
    val r = scala.util.Random
    r.nextInt(info.n)
  }
}

case class BoxSpace(high: List[Double], low: List[Double], name: String, shape: List[Int])

case class ObservationSpace(info:BoxSpace)

//Classic Control Responses

case class ObservationClassicControl(observation: List[Double]) extends  Observation

case class StepReplyClassicControl(observation: List[Double], reward: Double, done: Boolean, info: Map[String, Int]) extends StepReply(reward,done,info)


//Atari Responses

case class ObservationAtari(observation: List[List[(Double,Double,Double)]]) extends Observation

case class StepReplyAtari(observation: List[List[(Double,Double,Double)]], reward: Double, done: Boolean, info: Map[String, Int]) extends StepReply(reward, done, info)

//https://doc.akka.io/docs/akka/2.4.5/scala/http/routing-dsl/directives/marshalling-directives/entity.html
//Unmarshalls the request entity to the given type and passes it to its inner Route

//General Responses
object EnvInstance extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val EnvInstanceFormats = jsonFormat1(EnvInstance.apply)
}

object ListEnvs extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val ListEnvsFormats = jsonFormat1(ListEnvs.apply)
}

object DiscreteSpace extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val DiscreteSpaceFormats = jsonFormat2(DiscreteSpace.apply)
}

object ActionSpace extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val ActionSpaceFormats = jsonFormat1(ActionSpace.apply)
}

object BoxSpace extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val BoxSpaceFormats = jsonFormat4(BoxSpace.apply)
}

object ObservationSpace extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val ObservationSpaceFormats = jsonFormat1(ObservationSpace.apply)
}

//Classic Control

object ObservationClassicControl extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val ObservationFormats = jsonFormat1(ObservationClassicControl.apply)
}

object StepReplyClassicControl extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val StepReplyFormats = jsonFormat4(StepReplyClassicControl.apply)
}

//Atari Environments

object ObservationAtari extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val ObservationFormats = jsonFormat1(ObservationAtari.apply)
}

object StepReplyAtari extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val StepReplyFormats = jsonFormat4(StepReplyAtari.apply)
}
