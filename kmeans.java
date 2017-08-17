/*
	K-Means master
	Copyright by yusril 2017
*/

import java.time.temporal.JulianFields;
import java.util.Arrays;
import java.util.Random;

import com.mysql.jdbc.UpdatableResultSet;

public class KMeans {
	public static double[][] centroidBaru;
	public static double fungsiObjektif = 0;
	public static double[][] InisialisasiCentroid(
			int jumlahKelas, int jumlahParam) {
		Random random = new Random();
		double[][] hasil = new double[jumlahKelas][jumlahParam];
		for (int i = 0; i < jumlahKelas; i++) {
			for (int j = 0; j < jumlahParam; j++) {
				hasil[i][j] = random.nextInt(100) + 0;
			}
		}
		
		return hasil;
	}
	
	public static double[][] HitungEuclidean(double[][] data, double[][] centroid) {
		double[][] hasil = new double[data.length][centroid.length];
		for (int i = 0; i < hasil.length; i++) {
			for (int j = 0; j < hasil[0].length; j++) {
				double temp = 0;
				for (int k = 0; k < centroid[0].length; k++) {
					temp += Math.pow(data[i][k] - centroid[j][k], 2);
				}
				hasil[i][j] = Math.sqrt(temp);
			}
		}
		
		return hasil;
	}
	
	public static int Kelas(double[] data) {
		double minValue = data[0];
		for (int i = 0; i < data.length; i++) {
            if (data[i] < minValue ) {
                minValue = data[i];
            }
        }
		
		for (int i = 0; i < data.length; i++) {
			if (minValue == data[i])
				return i;
		}
		
		return 0;
	}
	
	public static void UpdateCentroid(double[][] data, 
			int[] indeks, double[][] centroid, int jumlahKelas) {
		centroidBaru = new double[centroid.length][centroid[0].length];
		int[] count = new int[centroid.length];
		fungsiObjektif = 0;
		for (int i = 0; i < centroidBaru.length; i++)
			for (int j = 0; j < centroidBaru[0].length; j++)
				centroidBaru[i][j] =  0;
		for (int i = 0; i < data.length; i++) {
			int cn = indeks[i];
			for (int j = 0; j < centroid[0].length; j++) {
				centroidBaru[cn][j] += data[i][j];
			}
			count[cn]++;
		}
		for (int i = 0; i < centroidBaru.length; i++) {
			for (int j = 0; j < centroidBaru[0].length; j++){
				try {
					if (centroidBaru[i][j] == 0) 
						centroidBaru[i][j] = centroid[i][j];
					else {
						centroidBaru[i][j] /= count[i]; 
						fungsiObjektif = fungsiObjektif + centroidBaru[i][j];
					}
					System.out.print(centroidBaru[i][j] + "\t");
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			System.out.println();
		}
		
	}
	
	public static double[][] AmbilNilaiCentroid() {
		return centroidBaru;
	}
	
	public static double AmbilFungsiObjektif() {
		return fungsiObjektif;
	}
	
	public static void main(String[] args) {
		double[][] data = new double[][] {
			{80,50,30,50,40,50,50,70},
			{83,80,60,60,70,70,60,70},
			{90,70,70,78,76,60,56,54},
			{40,80,20,40,40,30,60,20},
			{50,20,40,60,70,30,50,20},
			{50,40,50,60,50,60,70,40},
			{80,50,30,50,40,50,50,70},
			{83,80,60,60,70,70,60,70},
			{90,70,70,78,76,60,56,54},
			{40,80,20,40,40,30,60,20},
			{50,20,40,60,70,30,50,20},
			{50,40,50,60,50,60,70,40},
			{30,40,30,43,43,45,34,53},
			{50,50,40,50,60,50,40,50},
			{70,78,67,77,65,76,86,77},
			{30,20,40,30,30,40,30,30},
			{25,26,27,34,34,46,67,56},
			
		};
		double treshold = 0.1;
		double[][] init = InisialisasiCentroid(9, data[0].length);
		
		double counter = 0;
		int jmlIterasi = 0;
		double tr = 0;
		double[] jarakMin = new double[data.length];
		do {
			double counter1 = 0;
//			System.out.println("\n\nEuclidean Distance");
			double[][] jarak = HitungEuclidean(data, init);
			int indeksMinValue[] = new int[jarak.length];
			for (int i = 0; i < jarak.length; i++) {
				/*for (int j = 0; j < jarak[i].length; j++) {
					System.out.print(jarak[i][j] + "\t");
				}*/
				
				indeksMinValue[i] = Kelas(jarak[i]);
				jarakMin[i] = jarak[i][indeksMinValue[i]];
				counter1 = counter1 + jarakMin[i];
//				System.out.print(i + " ==> Min = " + indeksMinValue[i]);
			}
//			System.out.println(counter1);
//			System.out.println("\nUpdate Centroid:" +init.length +"\t"+init[0].length);
			UpdateCentroid(data, indeksMinValue, init, jarak[0].length);
			for (int i = 0; i < init.length; i++) {
				for (int j = 0; j < init[0].length; j++) {
					init[i][j] = 0;
				}
			}
			init = AmbilNilaiCentroid();
			System.out.print(counter +" - " + counter1);
			counter -= counter1;
			tr = Math.abs(counter);
			counter = counter1;
			System.out.println(" = "+tr);
//			if (counter < treshold) 
//				break;
			jmlIterasi++;
		} while (/*jmlIterasi < 10*/tr > treshold);

		System.out.println(jmlIterasi);
	}
	
	
	System.out.println("\tCopryright by yusril-2017")
}
