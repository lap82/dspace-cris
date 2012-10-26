package it.cilea.hku.authority.webui.controller.jdyna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import it.cilea.hku.authority.model.ACrisObject;
import it.cilea.osd.jdyna.controller.FormAddToNestedDefinitionController;
import it.cilea.osd.jdyna.model.ADecoratorNestedPropertiesDefinition;
import it.cilea.osd.jdyna.model.ADecoratorTypeDefinition;
import it.cilea.osd.jdyna.model.ANestedPropertiesDefinition;
import it.cilea.osd.jdyna.model.ATypeNestedObject;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;
import it.cilea.osd.jdyna.widget.WidgetPointer;

public class FormAddWidgetPointerToNestedPDController<TP extends ANestedPropertiesDefinition, DTP extends ADecoratorNestedPropertiesDefinition<TP>, ATN extends ATypeNestedObject<TP>, DTT extends ADecoratorTypeDefinition<ATN, TP>, H extends IPropertyHolder<Containable>, T extends Tab<H>, C extends ACrisObject>
        extends
        FormAddToNestedDefinitionController<WidgetPointer, TP, DTP, ATN, DTT, H, T>
{

    private Class<C> crisModel;
    
    public FormAddWidgetPointerToNestedPDController(Class<TP> targetModel,
            Class<WidgetPointer> renderingModel, Class<H> boxModel,
            Class<DTT> typeModel, Class<C> crisModel)
    {
        super(targetModel, renderingModel, boxModel, typeModel);
        this.crisModel = crisModel;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        String rendering = request.getParameter("renderingparent");
        String boxId = request.getParameter("boxId");
        String tabId = request.getParameter("tabId");
        DTP object = (DTP)command;
        ((WidgetPointer)(object.getReal().getRendering())).setTarget(crisModel.getCanonicalName());
        DTT rPd = getApplicationService().get(getTypeModel(), Integer.parseInt(rendering));
        
        object.getReal().setAccessLevel(rPd.getAccessLevel());
        getApplicationService().saveOrUpdate(object.getDecoratorClass(), object);      
        
        return new ModelAndView(getSuccessView()+"?pDId="+rPd.getReal().getId()+"&boxId="+boxId+"&tabId="+tabId);
    }    
}
