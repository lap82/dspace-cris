package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.ACrisObject;
import it.cilea.osd.jdyna.controller.FormDecoratorPropertiesDefinitionController;
import it.cilea.osd.jdyna.model.ADecoratorPropertiesDefinition;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;
import it.cilea.osd.jdyna.widget.WidgetPointer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class FormWidgetPointerDecoratorPDController<TP extends PropertiesDefinition, DTP extends ADecoratorPropertiesDefinition<TP>, H extends IPropertyHolder<Containable>, T extends Tab<H>, C extends ACrisObject>
        extends FormDecoratorPropertiesDefinitionController<WidgetPointer, TP, DTP, H, T>
{

    private Class<C> crisModel;
    
    public FormWidgetPointerDecoratorPDController(Class<TP> targetModel,
            Class<WidgetPointer> renderingModel, Class<H> boxModel, Class<C> crisModel)
    {
        super(targetModel, renderingModel, boxModel);
        this.crisModel = crisModel;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {

        String boxId = request.getParameter("boxId");
        String tabId = request.getParameter("tabId");

        DTP object = (DTP) command;
        ((WidgetPointer)(object.getReal().getRendering())).setTarget(crisModel.getCanonicalName());
        getApplicationService().saveOrUpdate(object.getDecoratorClass(), object);

        if (boxId != null && !boxId.isEmpty())
        {
            H box = getApplicationService().get(getBoxModel(),
                    Integer.parseInt(boxId));
            box.getMask().add(object);
            getApplicationService().saveOrUpdate(getBoxModel(), box);
        }
        return new ModelAndView(getSuccessView() + "?id=" + boxId + "&tabId="
                + tabId);
    }

}