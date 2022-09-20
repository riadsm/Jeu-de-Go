package View;

/** Interface Observer pour le click d'un Component
 *
 * Le listener est basé sur le model de l'Observer
 * Cela permet de séparer la logique(Le controller) de l'affichage(Tile)
 * Le comportement désiré lorsqu'on appuie sur un bouton est défini dans Controller
 * Celui qui appelle la méthode est une Tile.      
 *
 * Game permet de lier la classe Controller et Tile sans qu'aucun des deux ne se connaissent. 
 *
 *  Initialisation :: Controller --> Game --> BackgroundPane --> BackgroundGrid --> BackgroundTile
 *  Utilisation    :: BackgroundTile.onClick -->(appelle)--> Controller.onClick  
 *
 * Cela permet la réutilisabilité du Component Tile et pourrait permettre de changer le comportement des boutons en changeant le View.ClickListener.
 *  Controller() --> créé un nouveau listener
 * Game --> BackgroundTile.setClickListener(Controller.getListener)
 * BackgoundTile.ActionPerformed( listener.callMethod() --> va appeller la methode de Controller, sans avoir de relations avec le Controller)
 *
 *
 * Deux modes de pensées sont utilisées dans le modele de l'Observer
 * 1 :: la class EST un View.ClickListener et on donne une instance de l'objet dans SET_LISTENER
 * 2 :: La class possède une instance de View.ClickListener et on donne une référence de cet objet dans SET_Listener
 *
 *  La méthode 2 facilite l'encapsulation
 *  La méthode 1 facilite l'expension
 */

 public interface ClickListener {
     public void onClick(ClickEvent event);
 }
