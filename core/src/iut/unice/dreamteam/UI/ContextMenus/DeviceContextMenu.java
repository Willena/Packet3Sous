package iut.unice.dreamteam.UI.ContextMenus;

import iut.unice.dreamteam.Equipments.Computer;
import iut.unice.dreamteam.Equipments.Equipment;
import iut.unice.dreamteam.Interfaces.Packet;
import iut.unice.dreamteam.Protocols.ICMP;
import iut.unice.dreamteam.UI.Dialogs.EquipmentDialog;
import iut.unice.dreamteam.Utils.Debug;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.util.Optional;

/**
 * Created by Guillaume on 24/02/2017.
 */
public class DeviceContextMenu extends CustomContextMenu {
    private final Equipment equipement;
    private MenuItem editItem;
    private MenuItem deleteItem;
    private MenuItem duplicateItem;
    private MenuItem propertiesItem;
    private MenuItem pingCLI;

    public DeviceContextMenu(Equipment equipment) {
        super(equipment.getName());
        this.equipement = equipment;
        addMenuItems();
    }

    public void setEditAction(EventHandler<ActionEvent> event){
        editItem.setOnAction(event);
    }

    public void setDeleteAction(EventHandler<ActionEvent> event){
        deleteItem.setOnAction(event);
    }

    public void setDuplicateAction(EventHandler<ActionEvent> event){
        duplicateItem.setOnAction(event);
    }

    public void setPropertiesAction(EventHandler<ActionEvent> event){
        propertiesItem.setOnAction(event);
    }

    public void setPingCLI(EventHandler<ActionEvent> event){ pingCLI.setOnAction(event); }

    private void addMenuItems() {

        editItem = new MenuItem("Edit");
        editItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Debug.log("Edit " + equipement.getName());
                EquipmentDialog dialog  = new EquipmentDialog(equipement);

                dialog.showAndWait();
            }
        });

        deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Debug.log("Delete " + equipement.getName());
            }
        });

        duplicateItem = new MenuItem("Duplicate");
        duplicateItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Debug.log("Deplicate !");
            }
        });

        propertiesItem = new MenuItem("Properties");
        propertiesItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Debug.log("Properties");
            }
        });

        pingCLI = new MenuItem("Ping");
        pingCLI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Ping");
                dialog.setHeaderText("");
                dialog.setContentText("destination");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    Debug.log("resultat : " + (String) result.toString().substring(9, result.toString().length() - 1));

                    if (equipement.existService("icmp-client")) {
                        equipement.getService("icmp-client").initiateProtocol(equipement, equipement.getInterface(0), new JSONObject().put("ip-address", result.toString().substring(9, result.toString().length() - 1)));
                    }
                }

                dialog.close();
            }
        });
        getItems().addAll(editItem, duplicateItem, deleteItem, propertiesItem, pingCLI);
    }
}
