package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable

case class Day25[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day25(s)


  def sync: String = {
    s"""
      Don't you feel sometimes bored passing along the same parameters... again and again${????("Yes!")}
    """
    StartFold
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
    EndFold
    ????("AddressPersistentComponent should be implicit")
    object AddressPersistentComponent extends PersistentComponent[Address] {
      def preSave(m: Address): Address = m
    }
    ????("UserPersistentComponent should be implicit")
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
      ????("s should be implicit")
      def save[M<:Model](m:M)(s:PersistentComponent[M]):M = s.save(m)
    }
    import PersistentService._

    val bond = save(Character(name="Bond"))(UserPersistentComponent)
    val frodon = save(Character(name="Frodon", address=Some(Address(place="Shire"))))(UserPersistentComponent)
    val bilbon = save(Character(name="Bilbon", address=frodon.address))(UserPersistentComponent)
    val tatooine = save(Address(place="Tatooine"))(????("Remove this block..."))
    val luke = save(Character(name="Luke", address=Some(tatooine)))(????("Remove this block..."))

    val casting = List(bond, frodon, bilbon, luke)

    s"""
      <ul>
        <li>${casting.mkString("</li><li>")}</li>
      </ul>
    """
  }
}