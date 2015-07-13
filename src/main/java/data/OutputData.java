package data;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utilities.Utilities;

public class OutputData {
	private ArrayList<GeneticProgrammingTree> fittestTreeInEachGeneration = new ArrayList<GeneticProgrammingTree>();
	private ArrayList<Integer> populationSizeInEachGeneration = new ArrayList<Integer>();
	private long startTime = 0;
	private long currentTime = 0;
	private int generationCount = 0;

	private JFreeChart chart = null;
	private JFreeChart chart2 = null;
	private JFreeChart chart3 = null;
	private JFrame topFrame = null;
	private JPanel mainPanel = null;
	private ChartPanel chartpanel1 = null;
	private JPanel results = null;
	private JTextArea textArea = null;

	private volatile XYSeries series = new XYSeries("f(x) Real Funtion");
	private volatile XYSeries series2 = new XYSeries("f(x) Target Function");
	private volatile XYSeries series3 = new XYSeries(
			"Fittest Generation In Each Tree");
	private volatile XYSeries series4 = new XYSeries("Final Population/Fitness");

	public void setStartTime(long time) {
		startTime = time;
	}

	public void setCurrentTime(long time) {
		currentTime = time;
	}

	public void incrementGenerationCount() {
		generationCount++;
	}

	public void resetData() {
		fittestTreeInEachGeneration = new ArrayList<GeneticProgrammingTree>();
		populationSizeInEachGeneration = new ArrayList<Integer>();
		generationCount = 0;
	}

	public int getGenerationCount() {
		return generationCount;
	}

	public void addPopulationSizeInGeneration(int size) {
		populationSizeInEachGeneration.add(Integer.valueOf(size));
	}

	public void addFittestTreeInGeneration(GeneticProgrammingTree tree) {
		fittestTreeInEachGeneration.add(tree);
	}

	public void displayResults() throws Exception {
		printSeperatorLine();
		printResults();
		updateDashboard();
		printSeperatorLine();
		if (generationCount < 100 || generationCount > 10000) {
			generateBestFitnessGenerationChart();
		}
		// if(generationCount > 900)
		// generateBestFitnessGenerationChart();
	}

	public void displayPopulation(ArrayList<GeneticProgrammingTree> population) {
		int index = 0;
		for (GeneticProgrammingTree gpTree : population) {
			System.out.print("population[" + index + "]         = ");
			gpTree.inOrderPrint();
			System.out.println("population[" + index + "].fitness = "
					+ gpTree.getFitness());
			++index;
		}
	}

	private void printSeperatorLine() {
		System.out
				.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	public void displayFinalResults() throws Exception {
		printSeperatorLine();
		System.out
				.println("----------------------------------------------------------------*** Final Results ***-----------------------------------------------------------------------------");
		printResults();
		updateDashboard();
		textArea.setBackground(new Color(204, 255, 204));
		System.out.println("");
		if (fittestTreeInEachGeneration.get(generationCount - 1).getRoot()
				.depth() < 9) {
			Utilities.printTreeNode(fittestTreeInEachGeneration.get(
					generationCount - 1).getRoot());
		}
		generateBestFitnessGenerationChart();
		printSeperatorLine();
		printSeperatorLine();
	}

	private void generateBestFitnessGenerationChart() throws Exception {
		XYSeries series = new XYSeries("Best Fitness/Generation");

		int gen = 0;
		for (GeneticProgrammingTree gpTree : fittestTreeInEachGeneration) {
			series3.add(gen, gpTree.getFitness());
			++gen;
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart = ChartFactory.createXYLineChart(
				"Best Fitness/Generation", // Title
				"Generation", // x-axis Label
				"Best Fitness", // y-axis Label
				dataset, // Data set
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tool tips
				false // Generate URLs
				);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		final XYPlot plot1 = chart.getXYPlot();
		XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
		renderer1.setSeriesPaint(0, Color.GREEN);
		renderer1.setSeriesStroke(0, new BasicStroke(8.0f));
		plot1.setRenderer(renderer1);

		ChartUtilities.saveChartAsJPEG(new File("best_fitness_generation.jpg"),
				chart, 1500, 900);
	}

	public void recordInitialPopulationFitness(
			ArrayList<GeneticProgrammingTree> population) throws Exception {
		String fileName = "initial_population_fitness";
		recordFitnessOfPopulation(population, "Initial Population/Fitness",
				fileName);
	}

	public void recordFinalPopulationFitness(
			ArrayList<GeneticProgrammingTree> population) throws Exception {
		String fileName = "final_population_fitness";
		recordFitnessOfPopulation(population, "Final Population/Fitness",
				fileName);
	}

	private void recordFitnessOfPopulation(
			ArrayList<GeneticProgrammingTree> population, String title,
			String fileName) throws Exception {
		XYSeries series = new XYSeries(title);

		int t = 0;
		for (GeneticProgrammingTree gpTree : population) {
			series4.add(t, gpTree.getFitness());
			++t;
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart = ChartFactory
				.createXYLineChart(title, "Tree", "Fitness", dataset,
						PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		final XYPlot plot1 = chart.getXYPlot();
		XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
		renderer1.setSeriesPaint(0, Color.RED);
		renderer1.setSeriesStroke(1, new BasicStroke(9.0f));
		plot1.setRenderer(renderer1);

		ChartUtilities.saveChartAsJPEG(new File(fileName + ".jpg"), chart,
				1500, 900);
	}

	public void recordXYGraph() throws Exception {
		XYSeries series = new XYSeries("x/f(x)");

		for (TrainingData trainingData : TrainingData.getTrainingData()) {
			series.add(trainingData.inputData,
					fittestTreeInEachGeneration.get(generationCount - 1)
							.evaluate(trainingData.inputData));
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart2 = ChartFactory.createXYLineChart("x/f(x)", "x", "y",
				dataset, PlotOrientation.VERTICAL, true, true, false);

		final XYPlot plot1 = chart2.getXYPlot();
		XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
		renderer1.setSeriesPaint(0, Color.BLUE);
		renderer1.setSeriesStroke(1, new BasicStroke(7.0f));
		plot1.setRenderer(renderer1);
		ChartUtilities.saveChartAsJPEG(new File("xy_graph.jpg"), chart2, 1500,
				900);

	}

	public void loadDashboard() throws Exception {
		for (TrainingData trainingData : TrainingData.getTrainingData()) {
			series.add(trainingData.inputData,
					fittestTreeInEachGeneration.get(generationCount - 1)
							.evaluate(trainingData.inputData));
			series2.add(trainingData.inputData, trainingData.outputData);
		}

		displayResults();
	}

	public void createAndShowDashboard() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		XYSeriesCollection dataset2 = new XYSeriesCollection();
		dataset2.addSeries(series3);

		XYSeriesCollection dataset3 = new XYSeriesCollection();
		dataset3.addSeries(series2);

		JFrame.setDefaultLookAndFeelDecorated(true);
		topFrame = new JFrame("Genetic Programming System");

		mainPanel = new JPanel();
		GridLayout layout = new GridLayout(1, 2);

		layout.setVgap(11);
		mainPanel.setLayout(layout);

		JPanel mainPanel2 = new JPanel();

		BorderLayout bLayout = new BorderLayout();
		mainPanel2.setLayout(bLayout);
		results = new JPanel();

		textArea = new JTextArea(9, 80);

		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		textArea.setFont(new Font("Serif", Font.PLAIN, 16));
		results.add(scrollPane);

		topFrame.setSize(1000, 900);
		topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		topFrame.setResizable(true);
		topFrame.setVisible(true);

		chart2 = ChartFactory.createXYLineChart(
				"Fittes Tree In Each Generation", "Generation", "Fittest Tree",
				dataset2, PlotOrientation.VERTICAL, true, true, false);

		final XYPlot plot = chart2.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.GREEN);
		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
		plot.setRenderer(renderer);

		chart = ChartFactory.createXYLineChart("f(x) Fitness Function", "x",
				"y", dataset, PlotOrientation.VERTICAL, true, true, false);

		final XYPlot plot1 = chart.getXYPlot();
		XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
		renderer1.setSeriesPaint(0, Color.red);
		renderer1.setSeriesStroke(2, new BasicStroke(6.0f));
		plot1.setRenderer(renderer1);

		chart3 = ChartFactory.createXYLineChart("f(x) Target Function", "x",
				"y", dataset3, PlotOrientation.VERTICAL, true, true, false);

		final XYPlot plot2 = chart3.getXYPlot();
		XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
		renderer2.setSeriesPaint(0, Color.BLUE);
		renderer2.setSeriesStroke(2, new BasicStroke(6.0f));
		plot2.setRenderer(renderer2);

		ChartPanel chartpanel2 = new ChartPanel(chart2);
		ChartPanel chartpanel3 = new ChartPanel(chart3);
		chartpanel1 = new ChartPanel(chart);

		mainPanel.add(chartpanel3);
		mainPanel.add(chartpanel1);
		mainPanel.add(chartpanel2);

		mainPanel2.add(mainPanel, BorderLayout.NORTH);
		mainPanel2.add(results, BorderLayout.SOUTH);
		topFrame.getContentPane().add(mainPanel2);
	}

	public void updateDashboard() throws Exception {
		if (topFrame == null) {
			loadDashboard();
		}

		for (TrainingData trainingData : TrainingData.getTrainingData()) {
			series.update(trainingData.inputData, fittestTreeInEachGeneration
					.get(generationCount - 1).evaluate(trainingData.inputData));
		}

		showResults();
	}

	public void showResults() throws Exception {
		Date start = new Date(startTime);
		Date end = new Date(currentTime);

		// This is to format the your current date to the desired format
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		String startString = sdf.format(start);
		String endString = sdf.format(end);

		StringBuffer sb = new StringBuffer();
		sb.append("Start Time\t\t\t: " + startString + "\n");
		sb.append("Current Time\t\t\t: " + endString + "\n");
		sb.append("Elapsed Time\t\t\t: " + (currentTime - startTime)
				+ " milliseconds" + "\n");

		sb.append("Current Generation count\t\t: " + generationCount + "\n");
		sb.append("Current Generation Population size\t: "
				+ populationSizeInEachGeneration.get(generationCount - 1)
				+ "\n");

		sb.append("Fittest Solution\t\t: "
				+ fittestTreeInEachGeneration.get(generationCount - 1)
						.getExpression() + "\n");
		sb.append("Fittest Solution depth\t\t: "
				+ fittestTreeInEachGeneration.get(generationCount - 1).depth()
				+ "\n");
		sb.append("Fitness\t\t\t: "
				+ fittestTreeInEachGeneration.get(generationCount - 1)
						.getFitness() + "\n");

		textArea.setText(sb.toString());
	}

	private void printResults() throws Exception {
		Date start = new Date(startTime);
		Date end = new Date(currentTime);

		// This is to format the your current date to the desired format
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		String startString = sdf.format(start);
		String endString = sdf.format(end);
		System.out.println("Start Time                          : "
				+ startString);
		System.out
				.println("Current Time                        : " + endString);
		System.out.println("Elapsed seconds                     : "
				+ (currentTime - startTime) + " milliseconds");
		System.out.println("Current generation count            : "
				+ generationCount);
		System.out.println("Current generation population size  : "
				+ populationSizeInEachGeneration.get(generationCount - 1));
		System.out.print("Fittest Solution                    : ");
		fittestTreeInEachGeneration.get(generationCount - 1).inOrderPrint();
		System.out.println("Fittest Solution (trimmed)          : "
				+ fittestTreeInEachGeneration.get(generationCount - 1)
						.getExpression());
		System.out.println("Fittest Soluton depth               : "
				+ fittestTreeInEachGeneration.get(generationCount - 1).depth());
		System.out.println("Fitness                             : "
				+ fittestTreeInEachGeneration.get(generationCount - 1)
						.getFitness());
	}
}
