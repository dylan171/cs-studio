/* 
 * Copyright (c) 2006 Stiftung Deutsches Elektronen-Synchroton, 
 * Member of the Helmholtz Association, (DESY), HAMBURG, GERMANY.
 *
 * THIS SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN "../AS IS" BASIS. 
 * WITHOUT WARRANTY OF ANY KIND, EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR PARTICULAR PURPOSE AND 
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE. SHOULD THE SOFTWARE PROVE DEFECTIVE 
 * IN ANY RESPECT, THE USER ASSUMES THE COST OF ANY NECESSARY SERVICING, REPAIR OR 
 * CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE. 
 * NO USE OF ANY SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.
 * DESY HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, 
 * OR MODIFICATIONS.
 * THE FULL LICENSE SPECIFYING FOR THE SOFTWARE THE REDISTRIBUTION, MODIFICATION, 
 * USAGE AND OTHER RIGHTS AND OBLIGATIONS IS INCLUDED WITH THE DISTRIBUTION OF THIS 
 * PROJECT IN THE FILE LICENSE.HTML. IF THE LICENSE IS NOT INCLUDED YOU MAY FIND A COPY 
 * AT HTTP://WWW.DESY.DE/LEGAL/LICENSE.HTM
 */
package org.csstudio.sds.components.ui.internal.editparts;

import org.csstudio.sds.components.model.SimpleSliderModel;
import org.csstudio.sds.components.ui.internal.figures.SimpleSliderFigure;
import org.csstudio.sds.ui.editparts.AbstractWidgetEditPart;
import org.csstudio.sds.ui.editparts.IWidgetPropertyChangeHandler;
import org.csstudio.sds.ui.figures.IRefreshableFigure;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.UIJob;

/**
 * EditPart controller for the Slider widget. The controller mediates between
 * {@link SimpleSliderModel} and {@link AdvancedSliderFigure}.
 * 
 * @author Sven Wende & Stefan Hofer
 * 
 */
public final class SimpleSliderEditPart extends AbstractWidgetEditPart {

	/**
	 * A UI job, which is used to reset the manual value of the slider figure
	 * after a certain amount of time.
	 */
	private UIJob _resetManualValueDisplayJob;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IRefreshableFigure doCreateFigure() {
		final SimpleSliderModel model = (SimpleSliderModel) getCastedModel();

		final SimpleSliderFigure slider = new SimpleSliderFigure();
		slider.addSliderListener(new SimpleSliderFigure.ISliderListener() {
			public void sliderValueChanged(final double newValue) {
				model.getProperty(SimpleSliderModel.PROP_VALUE).setManualValue(
						newValue);

				slider.setManualValue((Double) newValue);

				if (_resetManualValueDisplayJob == null) {
					_resetManualValueDisplayJob = new UIJob("reset") {
						@Override
						public IStatus runInUIThread(
								final IProgressMonitor monitor) {
							slider.setManualValue(model.getValue());
							slider.setValue(model.getValue());
							return Status.OK_STATUS;
						}
					};

				}
				_resetManualValueDisplayJob.schedule(5000);

			}
		});

		slider.setPopulateEvents(false);
		slider.setMax(model.getMax());
		slider.setMin(model.getMin());
		slider.setIncrement(model.getIncrement());
		slider.setValue(model.getValue());
		slider.setShowValueAsText(model.getShowValueAsText());
		slider.setManualValue(model.getValue());
		slider.setOrientation(model.isHorizontal());
		slider.setDecimalPlaces(model.getPrecision());
		slider.setSliderWide(model.getSliderWide());
		slider.setPopulateEvents(true);
		return slider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void registerPropertyChangeHandlers() {
		// value
		IWidgetPropertyChangeHandler valHandler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IRefreshableFigure refreshableFigure) {
				SimpleSliderFigure slider = (SimpleSliderFigure) refreshableFigure;
				slider.setPopulateEvents(false);
				slider.setValue((Double) newValue);
				slider.setManualValue((Double) newValue);
				slider.setPopulateEvents(true);
				return true;
			}
		};
		setPropertyChangeHandler(SimpleSliderModel.PROP_VALUE, valHandler);

		// min
		IWidgetPropertyChangeHandler minHandler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IRefreshableFigure refreshableFigure) {
				SimpleSliderFigure slider = (SimpleSliderFigure) refreshableFigure;
				slider.setPopulateEvents(false);
				//slider.setMin((Integer) newValue);
				slider.setMin((Double) newValue);
				slider.setPopulateEvents(true);
				return true;
			}
		};
		setPropertyChangeHandler(SimpleSliderModel.PROP_MIN, minHandler);

		// max
		IWidgetPropertyChangeHandler maxHandler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IRefreshableFigure refreshableFigure) {
				SimpleSliderFigure slider = (SimpleSliderFigure) refreshableFigure;
				slider.setPopulateEvents(false);
				//slider.setMax((Integer) newValue);
				slider.setMax((Double) newValue);
				slider.setPopulateEvents(true);
				return true;
			}
		};
		setPropertyChangeHandler(SimpleSliderModel.PROP_MAX, maxHandler);

		// increment
		IWidgetPropertyChangeHandler incrementHandler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IRefreshableFigure refreshableFigure) {
				SimpleSliderFigure slider = (SimpleSliderFigure) refreshableFigure;
				slider.setPopulateEvents(false);
				//slider.setIncrement((Integer) newValue);
				slider.setIncrement((Double) newValue);
				slider.setPopulateEvents(true);
				return true;
			}
		};
		setPropertyChangeHandler(SimpleSliderModel.PROP_INCREMENT, incrementHandler);
		
		// precision
		IWidgetPropertyChangeHandler precisionHandler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IRefreshableFigure refreshableFigure) {
				SimpleSliderFigure slider = (SimpleSliderFigure) refreshableFigure;
				slider.setPopulateEvents(false);
				slider.setDecimalPlaces((Integer) newValue);
				slider.setPopulateEvents(true);
				return true;
			}
		};
		setPropertyChangeHandler(SimpleSliderModel.PROP_PRECISION, precisionHandler);
		
		// show value as text
		IWidgetPropertyChangeHandler showValueAsTextHandler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IRefreshableFigure refreshableFigure) {
				SimpleSliderFigure slider = (SimpleSliderFigure) refreshableFigure;
				slider.setShowValueAsText((Boolean)newValue);
				return true;
			}
		};
		setPropertyChangeHandler(SimpleSliderModel.PROP_SHOW_VALUE_AS_TEXT, showValueAsTextHandler);
		
		// minSliderWide
		IWidgetPropertyChangeHandler minSliderWideHandler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IRefreshableFigure refreshableFigure) {
				SimpleSliderFigure slider = (SimpleSliderFigure) refreshableFigure;
				slider.setPopulateEvents(false);
				slider.setSliderWide((Integer) newValue);
				slider.setPopulateEvents(true);
				return true;
			}
		};
		setPropertyChangeHandler(SimpleSliderModel.PROP_SLIDER_WIDE, minSliderWideHandler);

		// orientation
		IWidgetPropertyChangeHandler orientationHandler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IRefreshableFigure refreshableFigure) {
				SimpleSliderFigure slider = (SimpleSliderFigure) refreshableFigure;
				slider.setPopulateEvents(false);
				
				slider.setOrientation((Boolean) newValue);

				SimpleSliderModel model = (SimpleSliderModel) getModel();

				// invert the size of the element
				model.setSize(model.getHeight(), model.getWidth());
				slider.setPopulateEvents(true);
				return true;
			}
		};
		setPropertyChangeHandler(SimpleSliderModel.PROP_ORIENTATION,
				orientationHandler);

	}
}
