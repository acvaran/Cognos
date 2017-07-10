package wall_avoider.Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Formatter;

import cognos.GeneticAlgorithm.Individual;
import cognos.GeneticAlgorithm.Topology;

public class Game implements Topology {

	private Formatter fileName;
	private int generation;

	@Override
	public double test(Individual individual) {
		Map map = new Map();
		map.initializeMap((Character) individual);
		String str = "";
		int j = 0;
		// WallAvoider.updateGui(map.getMap());
		str = str + map + "\n";
		for (int i = 0; i < 1000; i++) {
			if (i % 8 == 0) {
				j = i;
				map.insertWall(map.createWall(3));
			}
			map.update();
			if (!map.getCharacter().isDead()) {
				if (i == j + 1)
					map.getCharacter().incrementFitness();
			} else {
				map.getCharacter().decrementFitness();
				break;
			}
			str = str + map + "\n";
			// WallAvoider.updateGui(map.getMap());

			map.getCharacter().move(map.getInput());
		}
//		str = str + map;
//		str = str.replace("\n", "a");
//		str = str.replace(" ", "");
//		File file3 = new File("C:\\Wall Avoider Populations");
//		if (!file3.exists()) {
//			file3.mkdir();
//		}
//		File file = new File("C:\\Wall Avoider Populations\\Generation" + generation);
//		if (!file.exists()) {
//			file.mkdir();
//		}
//		File file2 = new File(
//				"C:\\Wall Avoider Populations\\Generation" + generation + "\\" + ((Character) individual).getName());
//		if (!file2.exists()) {
//			file2.mkdir();
//			createFile(individual);
//			writeFile(individual, str);
//		}
		// WallAvoider.updateGui(map.getMap());
		return map.getCharacter().getFitness();
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public void createFile(Individual individual) {
		try {
			fileName = new Formatter("C:\\Wall Avoider Populations\\Generation" + generation + "\\"
					+ ((Character) individual).getName() + "\\map.txt");
		} catch (Exception e) {
			System.out.println("Failed!!!");
		}
	}

	public void writeFile(Individual individual, String str) {
		String line = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 'a') {
				fileName.format("%s%n", line);
				line = "";
			} else
				line = line + str.charAt(i);
		}
		fileName.format("%s%n", line);
		fileName.close();
	}

	public static void main(String[] args) {
		Population p = new Population(1000, 5, 2, 50, 1, 1, new Game());
		int generationNumber = 50;
		for (int i = 0; i < generationNumber; i++) {
			p.run();
			System.out.println("Generation: " + i);
			System.out.println(p.getInfo());
			p.selection();
			p.mutate();
		}
		System.out.println("Generation: " + generationNumber);
		System.out.println(p.getInfo());
	}
}
