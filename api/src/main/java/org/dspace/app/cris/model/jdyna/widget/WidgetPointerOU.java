package org.dspace.app.cris.model.jdyna.widget;

import it.cilea.osd.jdyna.widget.WidgetPointer;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.dspace.app.cris.model.jdyna.value.OUPointer;

@Entity
@Table(name = "cris_ou_wpointer")
public class WidgetPointerOU extends WidgetPointer<OUPointer>
{

}
