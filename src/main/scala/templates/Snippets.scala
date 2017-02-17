package templates

/**
 * Created by ytaras on 2/17/17.
 */
object Snippets {

  def combobox[K, V](name: String, selected: Option[K], dictionary: Seq[(K, V)]) =
    <select name={ name }>
      {
        dictionary.map {
          case (key, value) =>
            val isSelected = selected.contains(key)
            <option class="form-control" selected={ isSelected.toString } key={ key.toString }>{ value }</option>
        }
      }
    </select>

}
