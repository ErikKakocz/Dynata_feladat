package Dynata.SampleApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import model.Status;
import model.Participation;
import model.Member;
import model.Survey;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger logger = Logger.getLogger("Dynata.SampleApplication.App");
    private static Map<Long, ArrayList<Member>> membersBySurvey;
    private static Map<Long, ArrayList<Member>> membersEligibleForSurvey;
    private static Map<Long, ArrayList<Survey>> surveysByMember;
    private static Map<Long, ArrayList<Survey>> filteredSurveysByMember;
    
    private static ArrayList<Member> membersList;
    private static ArrayList<Survey> surveysList;
    
    private static final String PROMPTTEXT = "Please choose from the following options!\n1,Members who completed a specific survey."
            + "\n2,Surveys completed by a specific member.\n3,Points collected by a specific member."
            + "\n4,Members eligible for a specific survey.\n5,Statistics about all surveys.\n6,Exit,";

    private static final String POINTSCOLLECTEDTEXT = "%s collected %d points";
    private static final String SURVEYQUERY = "Enter the id of the survey to query!";
    private static final String MEMBERQUERY = "Enter the id of the member to query!";

    private static BufferedReader getFileReader(String fileName) {
        BufferedReader reader = null;
        try {
            logger.info("Getting filereader");
            File file = new File(new URI(App.class.getClassLoader().getResource(fileName).getFile()).getPath());
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info(reader.toString());
        return reader;
    }

    public static void printMembers(ArrayList<Member> members) {
        for (Member member : members)
            System.out.println("id: " + member.getId() + " name: " + member.getFullName());
    }

    public static void printSurveys(ArrayList<Survey> surveys) {
        for (Survey survey : surveys)
            System.out.println("id: " + survey.getSurveyId() + " name: " + survey.getName());
    }

    private static ArrayList<Member> loadMembers() {
        ArrayList<Member> result = new ArrayList<Member>();
        CSVParser parser = null;
        logger.info("Loading members...");
        try {
            parser = new CSVParser(getFileReader("OO - 2 - Members.csv"), CSVFormat.DEFAULT.withHeader());
            parser.getRecords().forEach((CSVRecord record) -> {
                try {
                    long id = Long.parseLong(record.get("Member Id"));
                    String name = record.get("Full name");
                    String email = record.get("E-mail address");
                    boolean active = Integer.parseInt(record.get("Is Active")) == 1;
                    result.add(new Member(id, name, email, active));
                } catch (Exception e) {
                    logger.error("Problem loading a member: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("Problem with member records" + " - " + e.getMessage());
        } finally {
            if (parser != null)
                try {
                    parser.close();
                } catch (IOException e) {
                    logger.error("Couldn't close parser: " + e.getMessage());
                }
        }
        return result;
    }

    private static ArrayList<Participation> loadParticipations() {
        ArrayList<Participation> result = new ArrayList<Participation>();
        CSVParser parser = null;
        logger.info("Loading participations..." + result.toString());
        try {
            parser = new CSVParser(getFileReader("OO - 2 - Participation.csv"), CSVFormat.DEFAULT.withHeader());
            parser.getRecords().forEach((CSVRecord record) -> {
                try {
                    long memberId = Long.parseLong(record.get("Member Id"));
                    long surveyId = Long.parseLong(record.get("Survey Id"));
                    Status status = Status.getStatus(Integer.parseInt(record.get("Status")));
                    int length = Integer.parseInt(record.get("Length"));
                    logger.info(memberId + "," + surveyId + "," + status + "," + length);
                    result.add(new Participation(memberId, surveyId, status, length));
                } catch (Exception e) {
                    logger.error("problem loading a participation: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("Problem with participation records" + " - " + e.getMessage());
        } finally {
            if (parser != null)
                try {
                    parser.close();
                } catch (IOException e) {
                    logger.error("Couldn't close parser: " + e.getMessage());
                }
        }
        return result;
    }

    private static ArrayList<Survey> loadSurveys() {
        ArrayList<Survey> result = new ArrayList<Survey>();
        CSVParser parser = null;
        logger.info("Loading surveys...");
        try {
            parser = new CSVParser(getFileReader("OO - 2 - Surveys.csv"), CSVFormat.DEFAULT.withHeader());
            parser.getRecords().forEach((CSVRecord record) -> {
                try {
                    long surveyId = Long.parseLong(record.get("Survey Id"));
                    String name = record.get("Name");
                    int expectedCompletes = Integer.parseInt(record.get("Expected completes"));
                    int completedPoints = Integer.parseInt(record.get("Completion points"));
                    int filteredPoints = Integer.parseInt(record.get("Filtered points"));
                    result.add(new Survey(surveyId, name, expectedCompletes, completedPoints, filteredPoints));
                } catch (Exception e) {
                    logger.error("problem loading a survey: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("problem loading surveys: " + e.getMessage());
        } finally {
            if (parser != null)
                try {
                    parser.close();
                } catch (IOException e) {
                    logger.error("Couldn't close parser: " + e.getMessage());
                }
        }
        return result;
    }

    public static void initData() {
        logger.info("initializing data");
        membersList = new ArrayList<Member>(loadMembers());
        surveysList = new ArrayList<Survey>(loadSurveys());
        ArrayList<Participation> participations = new ArrayList<Participation>(loadParticipations());

        surveysByMember = new HashMap<Long, ArrayList<Survey>>();
        membersBySurvey = new HashMap<Long, ArrayList<Member>>();
        membersEligibleForSurvey = new HashMap<Long, ArrayList<Member>>();

        filteredSurveysByMember = new HashMap<Long, ArrayList<Survey>>();

        membersList.forEach((Member member) -> {
            ArrayList<Survey> surveysForThisMember = new ArrayList<Survey>();
            ArrayList<Survey> filteredSurveysForThisMember = new ArrayList<Survey>();
            surveysList.forEach((Survey survey) -> {
                participations.forEach((Participation participation) -> {
                    if (member.getId() == participation.getMemberId()
                            && survey.getSurveyId() == participation.getSurveyId())
                        if (participation.getStatus().equals(Status.COMPLETED))
                            surveysForThisMember.add(survey);
                        else if (participation.getStatus().equals(Status.FILTERED))
                            filteredSurveysForThisMember.add(survey);
                });
            });
            surveysByMember.put(member.getId(), surveysForThisMember);
            filteredSurveysByMember.put(member.getId(), filteredSurveysForThisMember);
        });
        surveysList.forEach((Survey survey) -> {
            ArrayList<Member> membersForThisSurvey = new ArrayList<Member>();
            membersList.forEach((Member member) -> {
                participations.forEach((Participation participation) -> {
                    if (member.getId() == participation.getMemberId()
                            && survey.getSurveyId() == participation.getSurveyId()) {
                        membersForThisSurvey.add(member);
                    }
                });
            });
            membersBySurvey.put(survey.getSurveyId(), membersForThisSurvey);

        });
        ArrayList<Member> activeMembers = new ArrayList<Member>();
        membersList.forEach((Member member) -> {
            logger.debug("activemembers: " + member.toString());
            if (member.isActive()) {
                activeMembers.add(member);
            }
        });
        logger.debug("activemembers size:" + activeMembers.size());
        surveysList.forEach((Survey survey) -> {
            logger.debug("survey:" + survey.getSurveyId() + ". activemembers size:" + activeMembers.size());
            ArrayList<Member> eligible = new ArrayList<Member>(activeMembers);

            eligible.removeAll(membersBySurvey.get(survey.getSurveyId()));
            logger.debug("survey:" + survey.getSurveyId() + ". eligible size :" + activeMembers.size());
            membersEligibleForSurvey.put(survey.getSurveyId(), eligible);
        });
        
        
        logger.info("Initialization complete.");
    }

    public static ArrayList<Member> membersCompletedSurvey(Long surveyId) {
        return membersBySurvey.get(surveyId);
    }

    public static ArrayList<Member> membersEligibleForsurvey(long surveyId) {
        return membersEligibleForSurvey.get(surveyId);
    }

    public static ArrayList<Survey> surveyCompletedBy(Long memberId) {
        return surveysByMember.get(memberId);
    }

    public static int pointsCollectedby(long memberId) {
        int result = 0;
        ArrayList<Survey> surveysCompleted = surveysByMember.get(memberId);
        ArrayList<Survey> surveysFiltered = filteredSurveysByMember.get(memberId);
        for (Survey survey : surveysCompleted)
            result += survey.getCompletedPoints();
        for (Survey survey : surveysFiltered)
            result += survey.getFilteredPoints();
        return result;
    }

    public static void statistics() {
        
    }

    public static void main(String[] args) {
        initData();
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println(PROMPTTEXT);
                switch (scanner.nextInt()) {
                case 1: {
                    System.out.println(SURVEYQUERY);
                    printSurveys(surveysList);
                    ArrayList<Member> members = membersCompletedSurvey(scanner.nextLong() - 1);
                    printMembers(members);
                    break;
                }
                case 2: {
                    System.out.println(MEMBERQUERY);
                    printMembers(membersList);
                    ArrayList<Survey> surveys = surveyCompletedBy(scanner.nextLong() - 1);
                    printSurveys(surveys);
                    break;
                }
                case 3: {
                    System.out.println(MEMBERQUERY);
                    printMembers(membersList);
                    Member member = membersList.get(scanner.nextInt() - 1);
                    System.out.println(String.format(POINTSCOLLECTEDTEXT, member.getFullName(),
                            pointsCollectedby(member.getId())));
                    break;
                }
                case 4: {
                    System.out.println(SURVEYQUERY);
                    printSurveys(surveysList);
                    ArrayList<Member> members = membersEligibleForsurvey(scanner.nextLong() - 1);
                    printMembers(members);
                    break;
                }
                case 5: {
                    statistics();
                }
                case 6: {
                    exit = true;
                }

                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (scanner != null)
                scanner.close();
        }
    }

}
