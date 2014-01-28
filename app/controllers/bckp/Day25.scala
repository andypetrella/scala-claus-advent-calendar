package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable
import controllers.DayTmpl

case class Day25[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day25()


  def sync: String = {
    s"""
      Don't you feel sometimes bored passing along the same parameters... again and again${???}
    """
    trait Model {
      val id:Option[Long]
      def withId(id:Long):this.type
    }
    case class Character(id:Option[Long] = None, name:String, address:Option[Address] = None) extends Model {
      def withId(id: Long): Character.this.type = copy(id=Some(id)).asInstanceOf[Character.this.type]
    }
    case class Address(id:Option[Long]=None, place:String) extends Model {
      def withId(id: Long): Address.this.type = copy(id=Some(id)).asInstanceOf[Address.this.type]
    }

    trait PersistentComponent[M<:Model] {
      val store: collection.mutable.Map[Long, M] = new mutable.HashMap[Long, M]()
      def get(id:Long):Option[M] = store.get(id)
      def preSave(m:M):M
      def save(m:M):M = {
        //presaving stuffs
        val pm = preSave(m)
        //get|generate id
        val id = pm.id.getOrElse(util.Random.nextLong())
        //store it with its id
        store += (id -> pm.withId(id=id))
        //return the stored object
        store(id)
      }
    }
    object AddressPersistentComponent extends PersistentComponent[Address] {
      def preSave(m: Address): Address = m
    }
    object UserPersistentComponent extends PersistentComponent[Character] {
      def preSave(m: Character): Character = {
        val userUpdatedOpt = m.address.map{a =>
          //if address is defined => save it before
          val address = AddressPersistentComponent.save(a)
          // and update the current user
          m.copy(address = Some(address))
        }
        userUpdatedOpt.getOrElse(m)
      }
    }

    object PersistentService {
      def save[M<:Model](m:M)(s:PersistentComponent[M]):M = s.save(m)
    }
    import PersistentService._

    val bond = save(Character(name="Bond"))(UserPersistentComponent)
    val frodon = save(Character(name="Frodon", address=Some(Address(place="Shire"))))(UserPersistentComponent)
    val bilbon = save(Character(name="Bilbon", address=frodon.address))(UserPersistentComponent)
    val tatooine = save(Address(place="Tatooine"))(???)
    val luke = save(Character(name="Luke", address=Some(tatooine)))(???)

    val casting = List(bond, frodon, bilbon, luke)

    s"""
      <ul>
        <li>${casting.mkString("</li><li>")}</li>
      </ul>
    """
  }
}