package md.akdev_service_management.sm.models.ticket;

import md.akdev_service_management.sm.models.catalogue.Catalogue;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ticket_priority")
public class TicketPriority extends Catalogue {

}
