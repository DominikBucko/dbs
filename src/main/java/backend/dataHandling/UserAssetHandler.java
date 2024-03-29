package backend.dataHandling;

import backend.entity.Asset;
import backend.entity.Ticket;
import backend.entity.User;
import backend.service.AssetService;
import backend.service.TicketService;
import backend.service.UserService;

import java.util.List;

public class UserAssetHandler {
    AssetService assetService;
    UserService userService;
    TicketService ticketService;
    User currentUser;

    public UserAssetHandler(String username) {
        assetService = new AssetService();
        userService = new UserService();
        ticketService = new TicketService();
        currentUser = userService.getUserByUsername(username);
    }

    public List<Asset> getAvailableAssets(int limit, int offset) {
        List<Asset> assets = assetService.getAvailable(currentUser.getDepartment(), limit, offset);
        return assets;
    }

    public List<Ticket> getUserTickets() {
        return this.ticketService.getUserTickets(currentUser);
    }

    public boolean createTicket(Asset asset) {
        Ticket newTicket = new Ticket(currentUser, asset);
        return ticketService.saveTicket(newTicket);
    }

    public void askForReturn(Ticket currentTicket) {
        ticketService.updateTicket(currentTicket);
    }
}
